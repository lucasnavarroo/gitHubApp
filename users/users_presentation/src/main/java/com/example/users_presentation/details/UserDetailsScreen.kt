package com.example.users_presentation.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core.LocalSpacing
import com.example.core.UiEvent
import com.example.githubapp.R
import com.example.users_presentation.components.UserHeader
import com.example.users_presentation.components.UserTextInfo

@Composable
fun UserDetailsScreen(
    login: String,
    scaffoldState: ScaffoldState,
    viewModel: UserDetailsViewModel,
    onBackClick: () -> Unit,
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current
    val state = viewModel.state

    LaunchedEffect(key1 = true) {
        viewModel.onEvent(UserDetailsEvent.OnLoad(login))
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        context.getString(event.message)
                    )
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        takeIf { (!state.isLoading) }?.let {
            Column(
                modifier = Modifier
                    .padding(horizontal = spacing.spaceMedium)
                    .testTag("user_details_screen")
            ) {
                Spacer(modifier = Modifier.height(spacing.spaceLarge))
                Image(
                    modifier = Modifier
                        .clickable { onBackClick() }
                        .testTag("icon_back"),
                    painter = painterResource(id = R.drawable.baseline_arrow_back_ios_24),
                    contentDescription = stringResource(R.string.back),
                )
                Spacer(modifier = Modifier.height(spacing.spaceLarge))
                state.user?.let { user ->
                    UserHeader(
                        user = user,
                        iconSize = 90.dp,
                        isIconVisible = false,
                        titleStyle = MaterialTheme.typography.h6,
                        subTitleStyle = MaterialTheme.typography.subtitle1
                    )
                    Spacer(modifier = Modifier.height(spacing.spaceMedium))
                    takeIf { user.bio.isNotEmpty() }?.let {
                        Text(
                            text = stringResource(R.string.about_me),
                            style = MaterialTheme.typography.h6
                        )
                        Text(
                            text = user.bio,
                            style = MaterialTheme.typography.body2
                        )
                        Spacer(modifier = Modifier.height(spacing.spaceMedium))
                    }
                    takeIf { user.name.isNotEmpty() }?.let {
                        UserTextInfo(
                            modifier = Modifier.fillMaxWidth(),
                            title = stringResource(R.string.name),
                            value = user.name
                        )
                        Spacer(modifier = Modifier.height(spacing.spaceMedium))
                    }
                    takeIf { user.company.isNotEmpty() }?.let {
                        UserTextInfo(
                            modifier = Modifier.fillMaxWidth(),
                            title = stringResource(R.string.company),
                            value = user.company
                        )
                        Spacer(modifier = Modifier.height(spacing.spaceMedium))
                    }
                    takeIf { user.location.isNotEmpty() }?.let {
                        UserTextInfo(
                            modifier = Modifier.fillMaxWidth(),
                            title = stringResource(R.string.location),
                            value = user.location
                        )
                        Spacer(modifier = Modifier.height(spacing.spaceMedium))
                    }
                    takeIf { user.email.isNotEmpty() }?.let {
                        UserTextInfo(
                            modifier = Modifier.fillMaxWidth(),
                            title = stringResource(R.string.email),
                            value = user.email,
                        )
                        Spacer(modifier = Modifier.height(spacing.spaceMedium))
                    }
                    UserTextInfo(
                        modifier = Modifier.fillMaxWidth(),
                        title = stringResource(R.string.public_repos),
                        value = user.publicRepos,
                    )
                    Spacer(modifier = Modifier.height(spacing.spaceLarge))
                    Text(
                        text = stringResource(R.string.my_repos),
                        style = MaterialTheme.typography.h6
                    )
                }
                state.repos?.let {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        this.items(it) {
                            Column(
                                modifier = Modifier
                                    .testTag("repo_item")
                            ) {
                                Text(
                                    it.name
                                )
                            }
                        }
                    }
                }
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .testTag("loading_box"),
        contentAlignment = Alignment.Center
    ) {
        when {
            state.isLoading -> CircularProgressIndicator(color = Color.Black)
            state.nothingFound -> {
                Text(
                    stringResource(R.string.nothing_found)
                )
            }
        }
    }
}