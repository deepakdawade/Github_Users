package com.dev2d.githubusers.networking.result

import android.util.Log
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withTimeout
import java.util.concurrent.TimeUnit


abstract class InvokeUseCase<in P> {
    operator fun invoke(params: P, timeoutMs: Long = defaultTimeoutMs): Flow<InvokeStatus<Unit>> {
        return flow {
            try {
                withTimeout(timeoutMs) {
                    emit(InvokeStarted)
                    doWork(params)
                    emit(InvokeSuccess(Unit))
                }
            } catch (e: TimeoutCancellationException) {
                emit(InvokeError(e))
            }
        }.catch { error ->
            Log.e(javaClass.name, error.stackTraceToString())
            emit(InvokeError(error))
        }
    }

    protected abstract suspend fun doWork(params: P)

    companion object {
        private val defaultTimeoutMs = TimeUnit.MINUTES.toMillis(5)
    }
}


abstract class SubjectUseCase<P : Any, T> {
    private val paramState = MutableSharedFlow<P>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val flow: Flow<T> = paramState
        .distinctUntilChanged()
        .flatMapLatest { createObservable(it) }
        .distinctUntilChanged()
        .catch { error ->
            Log.e(javaClass.name, error.stackTraceToString())
        }

    operator fun invoke(params: P) {
        paramState.tryEmit(params)
    }

    protected abstract fun createObservable(params: P): Flow<T>
}

