package com.example.users_domain.repository

import com.example.users_domain.model.Repository
import com.example.users_domain.model.User

interface UsersRepository {
    suspend fun getUsers(): Result<List<User>>
    suspend fun searchUser(userName: String): Result<User>
    suspend fun getUserRepositories(userName: String): Result<List<Repository>>
}