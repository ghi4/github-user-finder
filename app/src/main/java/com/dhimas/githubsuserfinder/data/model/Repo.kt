package com.dhimas.githubsuserfinder.data.model

import com.google.gson.annotations.SerializedName

class Repo(
    @SerializedName("full_name")
    val name: String? = null,

    @SerializedName("description")
    val description: String? = null
)