package com.dhimas.githubsuserfinder.data.model

import com.google.gson.annotations.SerializedName

class SearchResult {
    @SerializedName("items")
    val user: List<User>? = null

    @SerializedName("total_count")
    val total_count: Int? = 0
}