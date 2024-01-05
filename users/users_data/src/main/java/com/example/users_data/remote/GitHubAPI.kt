package com.example.users_data.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubAPI {

    @GET("users")
    suspend fun getUsers(): List<UserDTO>

    @GET("users/{userName}")
    suspend fun searchUser(
        @Path("userName") userName: String,
    ): UserDTO

    @GET("users/{userName}/repos")
    suspend fun getUserRepos(
        @Path("userName") userName: String,
    ): List<RepositoryDTO>

    companion object {
        const val BASE_URL = "https://api.github.com/"
    }
}