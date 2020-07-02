package com.dhimas.githubsuserfinder.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dhimas.githubsuserfinder.data.api.RetrofitFactory
import com.dhimas.githubsuserfinder.data.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailViewModel : ViewModel() {
    private val user = MutableLiveData<User>()

    fun setUsername(username: String) {
        val service = RetrofitFactory.makeRetrofitService()
        val call = service.getUserDetail(username)

        call.enqueue(object : Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("Info", "$t")
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    user.postValue(response.body() as User)
                }
            }
        })
    }

    fun getUser() = user

}