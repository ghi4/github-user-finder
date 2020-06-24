package com.dhimas.githubsuserfinder.data.model

import com.google.gson.annotations.SerializedName

class User {
    @SerializedName("login")
    val username: String? = null

    @SerializedName("name")
    val name: String? = null

    @SerializedName("avatar_url")
    val avatarUrl: String? = null

    @SerializedName("company")
    val company: String? = null

    @SerializedName("location")
    val location: String? = null

    @SerializedName("public_repos")
    val repo_count: String? = null
}