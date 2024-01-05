package com.example.users_domain.usecase

import com.example.users_domain.model.User
import com.example.users_domain.repository.UsersRepository

class GetUsersUseCase(
    private val repository: UsersRepository
) {
    suspend operator fun invoke(): Result<List<User>> = repository.getUsers()
}