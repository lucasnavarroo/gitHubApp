package com.example.users_data.remote

import com.google.gson.annotations.SerializedName

data class RepositoryDTO(
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("url")
    val url: String? = null,
    @SerializedName("language")
    val language: String? = null,
    @SerializedName("forks_count")
    val forks: Int? = null,
    @SerializedName("watchers_count")
    val watchers: Int? = null,
)