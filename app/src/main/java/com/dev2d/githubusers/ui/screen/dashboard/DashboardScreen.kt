package com.dev2d.githubusers.ui.screen.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.dev2d.githubusers.ui.screen.destinations.DetailScreenDestination
import com.dev2d.githubusers.util.rememberStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(start = true)
@Composable
fun DashboardScreen(
    navigator: DestinationsNavigator
) {
    val viewModel: DashboardViewModel = hiltViewModel()
    val uiState by rememberStateWithLifecycle(stateFlow = viewModel.uiState)
    DashboardScreenContent(
        uiState = uiState,
        onUpdateToggleState = viewModel::updateToggleState,
        onUserSelected = {
            val direction = DetailScreenDestination(it)
            navigator.navigate(direction)
        }
    )
}