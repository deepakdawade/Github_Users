package com.dev2d.githubusers.ui.screen.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev2d.githubusers.data.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor() : ViewModel() {
    private val loading = MutableStateFlow(false)
    private val users = MutableStateFlow(emptyList<User>())
    val uiState = combine(
        loading,
        users
    ) { loading, users ->
        DashboardScreenUiState.STATE.copy(
            users = users,
            loading = loading
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DashboardScreenUiState.STATE
    )
}