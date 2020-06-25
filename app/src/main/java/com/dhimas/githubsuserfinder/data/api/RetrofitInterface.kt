package com.dhimas.githubsuserfinder.data.api

import com.dhimas.githubsuserfinder.data.model.Repo
import com.dhimas.githubsuserfinder.data.model.SearchResult
import com.dhimas.githubsuserfinder.data.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitInterface {
    @GET("search/users")
    @Headers("Authorization: token c1beb69d93d5b0978ec533eed45763e9424e4880")
    fun getSearchResult(@Query("q") q: String): Call<SearchResult>

    @GET("users/{username}")
    @Headers("Authorization: token c1beb69d93d5b0978ec533eed45763e9424e4880")
    fun getUserDetail(@Path("username") username: String?): Call<User>

    @GET("users/{username}/{follow}?page=1&per_page=100")
    @Headers("Authorization: token c1beb69d93d5b0978ec533eed45763e9424e4880")
    fun getUserFollow(
        @Path("username") username: String,
        @Path("follow") follow: String
    ): Call<ArrayList<User>>

    @GET("/users/{username}/repos?page=1&per_page=100")
    fun getUserRepo(@Path("username") username: String): Call<ArrayList<Repo>>
}