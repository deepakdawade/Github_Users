package com.dev2d.githubusers.ui.screen.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev2d.githubusers.data.User
import com.dev2d.githubusers.ui.screen.destinations.DetailScreenDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val args = DetailScreenDestination.argsFrom(savedStateHandle)
    private val loading = MutableStateFlow(false)
    private val user = MutableStateFlow<User?>(null)
    val uiState = combine(
        loading,
        user
    ) { loading, user ->
        DetailScreenUiState.STATE.copy(
            user = user ?: User(nodeId = args.userId),
            loading = loading
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DetailScreenUiState.STATE
    )
}