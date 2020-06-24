package com.dhimas.githubsuserfinder.userdetail.followers

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dhimas.githubsuserfinder.data.api.RetrofitFactory
import com.dhimas.githubsuserfinder.data.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel : ViewModel() {
    private var listUser = MutableLiveData<ArrayList<User>>()

    fun setUsername(username: String, type: String) {
        loadData(username, type)
    }

    private fun loadData(username: String, type: String){
        val service = RetrofitFactory.makeRetrofitService()
        val call = service.getUserFollow(username, type)

        call.enqueue(object : Callback<ArrayList<User>> {
            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                Log.d("Throwable", t.message.toString())
            }

            override fun onResponse(call: Call<ArrayList<User>>, response: Response<ArrayList<User>>) {
                if (response.isSuccessful) {
                    listUser.postValue(response.body() as ArrayList<User>)
                    Log.d("Info", "Array: ${response.body()!!.size} " )
                }
            }
        })
    }

    fun getListUser(): MutableLiveData<ArrayList<User>> {
        Log.d("Info", "Yeay Call getUser")
        return listUser
    }
}