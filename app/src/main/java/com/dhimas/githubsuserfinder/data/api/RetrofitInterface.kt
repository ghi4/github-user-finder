package com.dhimas.githubsuserfinder.data.api

import com.dhimas.githubsuserfinder.data.model.SearchResult
import com.dhimas.githubsuserfinder.data.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitInterface {
    @GET("search/users")
    fun getSearchResult(@Query("q") q: String): Call<SearchResult>

    @GET("users/{username}")
    fun getUserDetail(@Path("username") username: String?): Call<User>

    @GET("users/{username}/followers")
    fun getUserFollower(@Path("username") username: String): Call<SearchResult>

    @GET("users/{username}/following")
    fun getUserFollowing(@Path("username") username: String): Call<SearchResult>

    @GET("users/{username}/{follow}")
    fun getUserFollow(@Path("username") username: String?, @Path("follow") follow: String): Call<ArrayList<User>>
}