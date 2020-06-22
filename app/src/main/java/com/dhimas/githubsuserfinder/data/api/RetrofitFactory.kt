package com.dhimas.githubsuserfinder.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {
    companion object {
        const val BASE_URL = "https://api.github.com"

        fun makeRetrofitService(): RetrofitInterface {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(RetrofitInterface::class.java)
        }
    }
}