package com.example.users_domain.model

data class User(
    val login: String,
    val avatarUrl: String,
    val profileUrl: String,
    val name: String,
    val company: String,
    val location: String,
    val email: String,
    val bio: String,
    val publicRepos: Int,
)