package com.dhimas.githubsuserfinder.api

import com.dhimas.githubsuserfinder.model.SearchResult
import com.dhimas.githubsuserfinder.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitInterface {
    @GET("search/users")
    fun getSearchResult(@Query("q") q: String): Call<SearchResult>

    @GET("users/{username}")
    fun getUserDetail(@Path("username") username: String): Call<User>

    @GET("users/{username}/followers")
    fun getUserFollower(@Path("username") username: String): Call<User>

    @GET("users/{username}/following")
    fun getUserFollowing(@Path("username") username: String): Call<User>
}