package com.dev2d.githubusers.ui.screen.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.dev2d.githubusers.util.rememberStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun DetailScreen(
    userId: String,
    navigator: DestinationsNavigator
) {
    val viewModel: DetailViewModel = hiltViewModel()
    val uiState by rememberStateWithLifecycle(stateFlow = viewModel.uiState)
    DetailScreenContent(
        uiState = uiState,
        onBack = { navigator.popBackStack() }
    )
}