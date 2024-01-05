package com.example.users_presentation.list.view.mappers

import com.example.users_domain.model.User
import com.example.users_presentation.list.model.UserUI

fun User.toPresentation() = UserUI(
    login = login,
    profileUrl = profileUrl,
    avatarUrl = avatarUrl,
    name = name,
    company = company,
    location = location,
    email = email,
    bio = bio,
    publicRepos = publicRepos.toString(),
)