package com.dev2d.githubusers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dev2d.githubusers.ui.screen.NavGraphs
import com.dev2d.githubusers.ui.theme.GithubUsersTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GithubUsersTheme {
                MainScreen()
            }
        }
    }
}

@Composable
private fun MainScreen() {
    DestinationsNavHost(
        modifier = Modifier.fillMaxSize(),
        navGraph = NavGraphs.root
    )
}
