package com.example.users_data.remote

import com.google.gson.annotations.SerializedName


data class UserDTO(
    @SerializedName("login")
    val login: String? = null,
    @SerializedName("avatar_url")
    val avatarUrl: String? = null,
    @SerializedName("html_url")
    val htmlUrl: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("company")
    val company: String? = null,
    @SerializedName("location")
    val location: String? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("bio")
    val bio: String? = null,
    @SerializedName("public_repos")
    val publicRepos: Int? = null,
)
