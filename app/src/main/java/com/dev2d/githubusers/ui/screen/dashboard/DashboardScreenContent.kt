package com.dev2d.githubusers.ui.screen.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.rounded.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dev2d.githubusers.data.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreenContent(
    uiState: DashboardScreenUiState,
    onUpdateToggleState: (state: DashboardScreenUiState.ToggleState) -> Unit,
    onUserSelected: (id: String) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Users")
                }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.Center
        ) {
            DashboardScreenUserListing(
                modifier = Modifier
                    .fillMaxSize(),
                users = uiState.users,
                selectedToggleState = uiState.toggleState,
                onUserSelected = onUserSelected,
                onUpdateToggleState = onUpdateToggleState
            )
            if (uiState.loading) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
private fun DashboardScreenUserListing(
    modifier: Modifier,
    users: List<User>,
    selectedToggleState: DashboardScreenUiState.ToggleState,
    onUserSelected: (id: String) -> Unit,
    onUpdateToggleState: (state: DashboardScreenUiState.ToggleState) -> Unit
) {
    val span = if (selectedToggleState == DashboardScreenUiState.ToggleState.LIST) 1 else 2
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(span),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        item(
            span = {
                GridItemSpan(maxLineSpan)
            }
        ) {
            DashboardScreenHeading(
                modifier = Modifier.fillMaxWidth(),
                count = users.count(),
                selectedState = selectedToggleState,
                onUpdateToggleState = onUpdateToggleState
            )
        }
        items(
            items = users,
            key = {
                it.id
            }) {
            DashboardScreenUser(
                modifier = Modifier.fillMaxWidth(),
                user = it,
                onUserSelected = onUserSelected
            )
        }
    }


}

@Composable
private fun DashboardScreenHeading(
    modifier: Modifier,
    count: Int,
    selectedState: DashboardScreenUiState.ToggleState,
    onUpdateToggleState: (state: DashboardScreenUiState.ToggleState) -> Unit
) {
    val isGridView = selectedState == DashboardScreenUiState.ToggleState.GRID
    Row(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 0.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "$count users", modifier = Modifier.weight(1F))
        FilledIconToggleButton(
            checked = isGridView,
            onCheckedChange = {
                val state = if (it) DashboardScreenUiState.ToggleState.GRID
                else DashboardScreenUiState.ToggleState.LIST
                onUpdateToggleState(state)
            }
        ) {
            val icon = if (isGridView) Icons.Rounded.ThumbUp else Icons.Default.List
            Icon(imageVector = icon, contentDescription = "Switch list mode")
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DashboardScreenUser(
    modifier: Modifier,
    user: User,
    onUserSelected: (id: String) -> Unit
) {
    Card(
        modifier = modifier,
        onClick = {
            onUserSelected(user.nodeId)
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = user.nodeId)
        }
    }
}