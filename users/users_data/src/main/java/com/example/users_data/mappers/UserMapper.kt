package com.example.users_data.mappers

import com.example.users_data.remote.UserDTO
import com.example.users_domain.model.User

fun UserDTO.toData(): User {
    return User(
        login = login ?: "",
        avatarUrl = avatarUrl ?: "",
        profileUrl = htmlUrl ?: "",
        name = name ?: "",
        company = company ?: "",
        location = location ?: "",
        email = email ?: "",
        bio = bio ?: "",
        publicRepos = publicRepos ?: 0,
    )
}