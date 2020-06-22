package com.dhimas.githubsuserfinder.model

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

class User {
    @SerializedName("login")
    private val username: String? = null

    @SerializedName("name")
    private val name: String? = null

    @SerializedName("avatar_url")
    private val avatar_url: String? = null

    @SerializedName("company")
    private val company: String? = null

    @SerializedName("location")
    private val location: String? = null
}