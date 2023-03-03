package com.dev2d.githubusers.networking.result

sealed class InvokeStatus<out T>
object InvokeStarted : InvokeStatus<Nothing>()
data class InvokeSuccess<T>(val data: T) : InvokeStatus<T>()
data class InvokeError(val throwable: Throwable) : InvokeStatus<Nothing>()







