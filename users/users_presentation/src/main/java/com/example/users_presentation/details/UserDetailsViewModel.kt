package com.example.users_presentation.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.UiEvent
import com.example.githubapp.R
import com.example.users_domain.usecase.GetRepositoriesUseCase
import com.example.users_domain.usecase.SearchUserUseCase
import com.example.users_presentation.list.model.RepositoriesUI
import com.example.users_presentation.list.model.UserUI
import com.example.users_presentation.list.view.mappers.toPresentation
import com.example.users_presentation.util.constants.ErrorCode
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class UserDetailsViewModel(
    private val searchUserUseCase: SearchUserUseCase,
    private val getRepositoriesUseCase: GetRepositoriesUseCase,
) : ViewModel() {

    var state by mutableStateOf(UserDetailsState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: UserDetailsEvent) {
        when (event) {
            is UserDetailsEvent.OnLoad -> {
                getUserAndRepos(event.login)
            }
        }
    }

    private fun getUserAndRepos(login: String) {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                user = null,
            )

            val user = flow {
                emit(searchUserUseCase(login))
            }
            val repos = flow {
                emit(getRepositoriesUseCase(login))
            }

            user.combine(repos) { userR, reposR ->
                val userResponse = userR.getOrNull()?.toPresentation()
                val reposResponse = reposR.getOrNull()?.map { it.toPresentation() }

                state = state.copy(
                    user = userResponse,
                    repos = reposResponse,
                    isLoading = false,
                    nothingFound = false,
                )
            }.catch {
                handleError(it)
            }.collect()
        }

    }

    private suspend fun handleError(error: Throwable) {
        state = state.copy(isLoading = false)

        when (error.message.toString().trim()) {
            ErrorCode.ERROR_404 -> {
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