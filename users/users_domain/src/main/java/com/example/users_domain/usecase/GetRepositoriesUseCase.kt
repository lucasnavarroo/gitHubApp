package com.example.users_domain.usecase

import com.example.users_domain.model.Repository
import com.example.users_domain.repository.UsersRepository

class GetRepositoriesUseCase(
    private val repository: UsersRepository
) {
    suspend operator fun invoke(
        userName: String
    ): Result<List<Repository>> = repository.getUserRepositories(userName)
}