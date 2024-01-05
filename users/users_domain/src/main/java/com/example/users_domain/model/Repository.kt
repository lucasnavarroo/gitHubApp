package com.example.users_domain.model

data class Repository(
    val name: String,
    val url: String,
    val language: String,
    val forks: Int,
    val watchers: Int,
)