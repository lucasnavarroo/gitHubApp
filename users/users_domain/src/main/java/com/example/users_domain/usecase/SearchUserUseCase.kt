package com.example.users_domain.usecase

import com.example.users_domain.model.User
import com.example.users_domain.repository.UsersRepository

class SearchUserUseCase(
    private val repository: UsersRepository
) {
    suspend operator fun invoke(
        userName: String
    ): Result<User> = repository.searchUser(userName)
}