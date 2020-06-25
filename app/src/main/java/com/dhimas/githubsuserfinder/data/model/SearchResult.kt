package com.dhimas.githubsuserfinder.data.model

import com.google.gson.annotations.SerializedName

class SearchResult {
    @SerializedName("items")
    val user: List<User>? = null
}