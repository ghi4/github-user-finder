package com.dhimas.githubsuserfinder.model

import com.google.gson.annotations.SerializedName

class SearchResult(
    @SerializedName("items")
    val user: List<User>? = null
)