package com.example.users_presentation.details

import com.example.users_presentation.list.model.RepositoriesUI
import com.example.users_presentation.list.model.UserUI

data class UserDetailsState(
    val isLoading: Boolean = false,
    val nothingFound: Boolean = false,
    val user: UserUI? = null,
    val repos: List<RepositoriesUI>? = null,
)