package com.dhimas.githubsuserfinder.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
class User(
    @PrimaryKey
    @SerializedName("id")
    val uid: Long,

    @SerializedName("login")
    val username: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("avatar_url")
    val avatarUrl: String? = null,

    @SerializedName("company")
    val company: String? = null,

    @SerializedName("location")
    val location: String? = null,

    @SerializedName("followers")
    val followersCount: String? = "0",

    @SerializedName("following")
    val followingCount: String? = "0",

    @SerializedName("public_repos")
    val repoCount: String? = "0"
)