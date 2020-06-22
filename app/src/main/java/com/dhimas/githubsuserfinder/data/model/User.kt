package com.dhimas.githubsuserfinder.data.model

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

class User {
    @SerializedName("login")
    val username: String? = null

    @SerializedName("name")
    val name: String? = null

    @SerializedName("avatar_url")
    val avatar_url: String? = null

    @SerializedName("company")
    val company: String? = null

    @SerializedName("location")
    val location: String? = null
}