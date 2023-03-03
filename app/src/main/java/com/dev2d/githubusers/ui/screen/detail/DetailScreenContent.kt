package com.dev2d.githubusers.ui.screen.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dev2d.githubusers.data.User
import com.dev2d.githubusers.networking.response.Subscription
import com.dev2d.githubusers.ui.theme.GithubUsersTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenContent(
    uiState: DetailScreenUiState,
    onBack: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Details",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Go Back")
                    }
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
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = uiState.user?.avatarUrl,
                    contentDescription = "User Avatar",
                    modifier = Modifier
                        .fillMaxWidth(0.5F)
                        .aspectRatio(1F)
                        .clip(CircleShape)
                )
                DetailScreenPager(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1F),
                    followers = uiState.followers,
                    subscriptions = uiState.subscriptions
                )
            }
            if (uiState.loading) {
                CircularProgressIndicator()
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun DetailScreenPager(
    modifier: Modifier,
    followers: List<User>,
    subscriptions: List<Subscription>
) {
    val coroutineScope = rememberCoroutineScope()
    val pages = listOf("Followers", "Subscribers")
    val pagerState = rememberPagerState()
    val context = LocalContext.current
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TabRow(
            // Our selected tab is our current page
            selectedTabIndex = pagerState.currentPage,
        ) {
            // Add tabs for all of our pages
            pages.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.scrollToPage(index)
                        }
                    },
                )
            }
        }

        HorizontalPager(
            count = pages.count(),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F),
            state = pagerState
        ) {
            val list = when (it) {
                0 -> DetailScreenFollowersListing(
                    modifier = Modifier.fillMaxSize(),
                    followers = followers
                )

                1 -> DetailScreenSubscribersListing(
                    modifier = Modifier.fillMaxWidth(),
                    subscriptions = subscriptions
                )

                else -> Unit
            }
        }
    }
}

@Composable
private fun DetailScreenFollowersListing(
    modifier: Modifier,
    followers: List<User>
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items = followers,
            key = {
                it.id
            }
        ) {
            DetailScreenFollower(modifier = Modifier.fillMaxWidth(), user = it)
        }
    }
}

@Composable
private fun DetailScreenFollower(
    modifier: Modifier,
    user: User
) {
    Card(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AsyncImage(
                model = user.avatarUrl,
                contentDescription = "User Avatar",
                modifier = Modifier
                    .fillMaxWidth(0.3F)
                    .aspectRatio(1F)
                    .clip(CircleShape),
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = user.nodeId,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
                Text(text = "login: ${user.login}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
private fun DetailScreenSubscribersListing(
    modifier: Modifier,
    subscriptions: List<Subscription>
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items = subscriptions,
            key = {
                it.id
            }
        ) {
            DetailScreenSubscription(modifier = Modifier.fillMaxWidth(), subscription = it)
        }
    }
}

@Composable
private fun DetailScreenSubscription(
    modifier: Modifier,
    subscription: Subscription
) {
    Card(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AsyncImage(
                model = subscription.owner.avatarUrl,
                contentDescription = "User Avatar",
                modifier = Modifier
                    .fillMaxWidth(0.3F)
                    .aspectRatio(1F)
                    .clip(CircleShape),
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = subscription.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                Text(text = subscription.full_name, style = MaterialTheme.typography.bodyMedium)
                Text(text = subscription.description, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4_XL)
@Composable
private fun PreviewDetailScreenContent() {
    GithubUsersTheme {
        DetailScreenContent(uiState = DetailScreenUiState.STATE) {

        }
    }
}