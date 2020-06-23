package com.dhimas.githubsuserfinder.userdetail.fragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dhimas.githubsuserfinder.data.api.RetrofitFactory
import com.dhimas.githubsuserfinder.data.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel : ViewModel() {
    private var listUsers = MutableLiveData<ArrayList<User>>()

    fun setUsername(username: String, type: String) {
        val service = RetrofitFactory.makeRetrofitService()
        val call = service.getUserFollow(username, type)

        call.enqueue(object : Callback<ArrayList<User>> {
            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                Log.d("Throwable", t.message.toString())
            }

            override fun onResponse(
                call: Call<ArrayList<User>>,
                response: Response<ArrayList<User>>
            ) {
                if (response.isSuccessful) {
                    listUsers.postValue(response.body() as ArrayList<User>)
                    Log.d("Info", "Array: ${response.body()!!.size} " )
                }
            }
        })
    }

    fun getUsers(): MutableLiveData<ArrayList<User>> {
        Log.d("Info", "Yeay Call getUser")
        return listUsers
    }
}