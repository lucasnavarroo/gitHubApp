package com.example.users_presentation.list.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core.LocalSpacing
import com.example.core.UiEvent
import com.example.githubapp.R
import com.example.users_presentation.components.SearchTextField
import com.example.users_presentation.components.UserHeader
import com.example.users_presentation.list.view.state.UserEvent
import com.example.users_presentation.list.viewmodel.UsersViewModel

@Composable
fun UsersScreen(
    scaffoldState: ScaffoldState,
    onDetailsClick: (login: String) -> Unit,
    viewModel: UsersViewModel,
) {
    val spacing = LocalSpacing.current
    val state = viewModel.state
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = true) {
        viewModel.onEvent(UserEvent.OnLoad)
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
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            modifier = Modifier.padding(
                vertical = spacing.spaceMedium,
                horizontal = spacing.spaceMedium
            ),
            text = stringResource(R.string.users_title),
            style = MaterialTheme.typography.h4
        )
        SearchTextField(
            modifier = Modifier.padding(horizontal = spacing.spaceMedium),
            text = state.login,
            onValueChange = {
                viewModel.onEvent(UserEvent.OnQueryChange(it))
            },
            shouldShowHint = state.isHintVisible,
            onSearch = {
                keyboardController?.hide()
                viewModel.onEvent(UserEvent.OnSearch)
            },
            onFocusChanged = {
                viewModel.onEvent(UserEvent.OnSearchFocusChange(it.isFocused))
            }
        )
        Spacer(modifier = Modifier.height(spacing.spaceLarge))
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(state.users) {
                Column(
                    modifier = Modifier
                        .clickable {
                            onDetailsClick(
                                it.login
                            )
                        }
                        .testTag("user_item")
                        .padding(horizontal = spacing.spaceMedium)
                ) {
                    UserHeader(
                        user = it,
                        iconSize = 80.dp,
                    )
                }
                Spacer(modifier = Modifier.height(spacing.spaceLarge))
                Divider(color = Color.Gray, thickness = 1.dp)
                Spacer(modifier = Modifier.height(spacing.spaceLarge))
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