package com.dev2d.githubusers.ui.screen.dashboard

import androidx.compose.runtime.Immutable
import com.dev2d.githubusers.data.User

@Immutable
data class DashboardScreenUiState(
    val loading: Boolean = false,
    val users: List<User> = emptyList()
) {
    companion object {
        val STATE = DashboardScreenUiState()
    }
}
