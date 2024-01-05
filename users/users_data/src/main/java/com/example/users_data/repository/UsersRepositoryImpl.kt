package com.example.users_data.repository

import com.example.users_data.remote.GitHubAPI
import com.example.users_data.mappers.toData
import com.example.users_domain.model.Repository
import com.example.users_domain.model.User
import com.example.users_domain.repository.UsersRepository

class UsersRepositoryImpl(
    private val api: GitHubAPI
) : UsersRepository {

    override suspend fun getUsers(): Result<List<User>> {
        return try {
            val gitUsersDTO = api.getUsers()
            Result.success(gitUsersDTO.map { it.toData() })
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun searchUser(userName: String): Result<User> {
        return try {
            val gitUserDTO = api.searchUser(userName)
            Result.success(gitUserDTO.toData())
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun getUserRepositories(userName: String): Result<List<Repository>> {
        return try {
            val reposDTO = api.getUserRepos(userName)
            Result.success(reposDTO.map { it.toData() })
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}