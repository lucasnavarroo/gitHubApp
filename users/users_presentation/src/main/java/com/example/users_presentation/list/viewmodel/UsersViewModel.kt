package com.example.users_presentation.list.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.UiEvent
import com.example.githubapp.R
import com.example.users_domain.usecase.GetUsersUseCase
import com.example.users_domain.usecase.SearchUserUseCase
import com.example.users_presentation.list.view.mappers.toPresentation
import com.example.users_presentation.list.view.state.UserEvent
import com.example.users_presentation.list.view.state.UserState
import com.example.users_presentation.util.constants.ErrorCode.Companion.ERROR_404
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class UsersViewModel(
    private val getUsersUseCase: GetUsersUseCase,
    private val searchUserUseCase: SearchUserUseCase
) : ViewModel() {

    var state by mutableStateOf(UserState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: UserEvent) {
        when (event) {
            UserEvent.OnLoad -> {
                getUsers()
            }

            is UserEvent.OnQueryChange -> {
                state = state.copy(login = event.query)
            }

            UserEvent.OnSearch -> {
                if (state.login.trim().isEmpty()) getUsers()
                else searchUser()
            }

            is UserEvent.OnSearchFocusChange -> {
                state = state.copy(
                    isHintVisible = !event.isFocused && state.login.isBlank()
                )
            }
        }
    }

    private fun getUsers() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                users = emptyList()
            )
            getUsersUseCase()
                .onSuccess { users ->
                    state = state.copy(
                        users = users.map { it.toPresentation() },
                        isLoading = false,
                        nothingFound = false,
                    )
                }
                .onFailure { handleError(it) }
        }
    }

    private fun searchUser() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                users = emptyList()
            )
            searchUserUseCase(state.login)
                .onSuccess { user ->
                    state = state.copy(
                        users = listOf(user.toPresentation()),
                        isLoading = false,
                        nothingFound = false,
                        login = ""
                    )
                }
                .onFailure { handleError(it) }
        }
    }

    private suspend fun handleError(error: Throwable) {
        state = state.copy(isLoading = false)

        when (error.message.toString().trim()) {
            ERROR_404 -> {
                state = state.copy(nothingFound = true)
            }

            else -> {
                _uiEvent.send(
                    UiEvent.ShowSnackbar(
                        R.string.error_message
                    )
                )
            }
        }
    }
}
