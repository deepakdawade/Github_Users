package com.dev2d.githubusers.ui.screen.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev2d.githubusers.data.User
import com.dev2d.githubusers.networking.domain.GetFollowersUseCase
import com.dev2d.githubusers.networking.domain.GetSubscribersUseCase
import com.dev2d.githubusers.networking.domain.GetUserUseCase
import com.dev2d.githubusers.networking.result.InvokeStarted
import com.dev2d.githubusers.networking.result.InvokeSuccess
import com.dev2d.githubusers.ui.screen.destinations.DetailScreenDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getUserUseCase: GetUserUseCase,
    private val getFollowersUseCase: GetFollowersUseCase,
    private val getSubscribersUseCase: GetSubscribersUseCase
) : ViewModel() {
    private val args = DetailScreenDestination.argsFrom(savedStateHandle)
    private val loading = MutableStateFlow(false)
    private val user = MutableStateFlow<User?>(null)
    private val followers = MutableStateFlow(emptyList<User>())
    private val subscribers = MutableStateFlow(emptyList<User>())
    val uiState = combine(
        loading,
        user,
        followers, subscribers
    ) { loading, user, followers, subscribers ->
        DetailScreenUiState.STATE.copy(
            user = user,
            loading = loading,
            followers = followers,
            subscribers = subscribers
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DetailScreenUiState.STATE
    )

    init {
        getUserUseCase(args.id)
        viewModelScope.launch {
            getUserUseCase.flow.collect {
                updateUser(it)
            }
        }

        viewModelScope.launch {
            getFollowersUseCase.invoke(args.id).collect {
                updateLoadingState(it is InvokeStarted)
                if (it is InvokeSuccess) {
                    updateFollowers(it.data)
                }
            }
        }
        viewModelScope.launch {
            getSubscribersUseCase.invoke(args.id).collect {
                updateLoadingState(it is InvokeStarted)
                if (it is InvokeSuccess) {
                    updateSubscribers(it.data)
                }
            }
        }
    }

    private fun updateUser(user: User) {
        this.user.update { user }
    }

    private fun updateLoadingState(loading: Boolean) {
        this.loading.update { loading }
    }

    private fun updateFollowers(followers: List<User>) {
        this.followers.update { followers }
    }

    private fun updateSubscribers(subscribers: List<User>) {
        this.subscribers.update { subscribers }
    }

}