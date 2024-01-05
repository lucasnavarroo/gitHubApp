package com.example.githubapp

import com.example.users_domain.model.Repository
import com.example.users_domain.model.User
import com.example.users_domain.repository.UsersRepository

class UsersRepositoryFake : UsersRepository {

    var shouldReturnError = false

    var usersResult = listOf<User>()

    var reposResult = listOf<Repository>()

    override suspend fun getUsers(): Result<List<User>> {
        return if (shouldReturnError) {
            Result.failure(Throwable())
        } else {
            Result.success(usersResult)
        }
    }

    override suspend fun searchUser(userName: String): Result<User> {
        return if (shouldReturnError) {
            Result.failure(Throwable())
        } else {
            Result.success(usersResult[0])
        }
    }

    override suspend fun getUserRepositories(userName: String): Result<List<Repository>> {
        return if (shouldReturnError) {
            Result.failure(Throwable())
        } else {
            Result.success(reposResult)
        }
    }
}
