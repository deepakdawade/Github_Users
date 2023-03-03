package com.dev2d.githubusers.ui.screen.detail

import androidx.compose.runtime.Immutable
import com.dev2d.githubusers.data.User

@Immutable
data class DetailScreenUiState(
    val loading: Boolean = false,
    val user: User? = null,
    val followers: List<User> = emptyList(),
    val subscribers: List<User> = emptyList(),
) {
    val userId: String get() = user?.nodeId ?: ""

    companion object {
        val STATE = DetailScreenUiState()
    }
}
