package com.example.users_presentation.list.view.state

import com.example.users_presentation.list.model.UserUI

data class UserState(
    val isLoading: Boolean = false,
    val nothingFound: Boolean = false,
    val login: String = "",
    val isHintVisible: Boolean = false,
    val users: List<UserUI> = emptyList()
)