package com.dev2d.githubusers.ui.screen.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev2d.githubusers.data.User
import com.dev2d.githubusers.networking.domain.FetchUsersUseCase
import com.dev2d.githubusers.networking.domain.GetUserUseCase
import com.dev2d.githubusers.networking.result.InvokeStarted
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val fetchUsersUseCase: FetchUsersUseCase,
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {
    private val loading = MutableStateFlow(false)
    private val toggleState = MutableStateFlow(DashboardScreenUiState.ToggleState.LIST)
    private val users = MutableStateFlow(emptyList<User>())
    val uiState = combine(
        loading,
        toggleState,
        users
    ) { loading, toggleState, users ->
        DashboardScreenUiState.STATE.copy(
            users = users,
            toggleState = toggleState,
            loading = loading
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DashboardScreenUiState.STATE
    )

    init {
        viewModelScope.launch {
            fetchUsersUseCase.invoke(Unit).collect {
                updateLoadingState(it is InvokeStarted)
            }
        }

        getUserUseCase.invoke(Unit)

        viewModelScope.launch {
            getUserUseCase.flow.collect {
                updateUsers(it)
            }
        }
    }

    private fun updateLoadingState(loading: Boolean) {
        this.loading.update { loading }
    }

    private fun updateUsers(users: List<User>) {
        this.users.update { users }
    }

    fun updateToggleState(state: DashboardScreenUiState.ToggleState) {
        this.toggleState.update { state }
    }
}