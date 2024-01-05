package com.example.users_data.mappers

import com.example.users_data.remote.RepositoryDTO
import com.example.users_domain.model.Repository

fun RepositoryDTO.toData(): Repository {
    return Repository(
        name = name ?: "",
        url = url ?: "",
        language = language ?: "",
        forks = forks ?: 0,
        watchers = watchers ?: 0
    )
}