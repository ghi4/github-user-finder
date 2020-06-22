package com.dhimas.githubsuserfinder.model

import com.google.gson.annotations.SerializedName

class SearchResult {
    @SerializedName("items")
    private val user: List<User>? = null

    @SerializedName("total_count")
    private val total_count: Int? = 0
}