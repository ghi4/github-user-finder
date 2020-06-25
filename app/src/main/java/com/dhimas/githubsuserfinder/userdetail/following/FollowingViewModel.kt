package com.dhimas.githubsuserfinder.userdetail.following

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dhimas.githubsuserfinder.data.api.RetrofitFactory
import com.dhimas.githubsuserfinder.data.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel : ViewModel() {
    private var listUser: MutableLiveData<ArrayList<User>>? = null

    fun getListUser(username: String): MutableLiveData<ArrayList<User>> {
        if(listUser == null){
            listUser = MutableLiveData()
            loadData(username)
        }
        return listUser as MutableLiveData<ArrayList<User>>
    }

    private fun loadData(username: String){
        val service = RetrofitFactory.makeRetrofitService()
        val call = service.getUserFollow(username, "following")

        call.enqueue(object : Callback<ArrayList<User>> {
            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                Log.d("Throwable", t.message.toString())
            }

            override fun onResponse(call: Call<ArrayList<User>>, response: Response<ArrayList<User>>) {
                if (response.isSuccessful) {
                    listUser?.postValue(response.body() as ArrayList<User>)
                }
            }
        })
    }
}