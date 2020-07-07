package com.dhimas.githubsuserfinder.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dhimas.githubsuserfinder.api.RetrofitFactory
import com.dhimas.githubsuserfinder.model.User
import com.dhimas.githubsuserfinder.database.FavoriteDatabase
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

    fun isUserFavorite(context: Context): Boolean {
        val dao = FavoriteDatabase.getInstance(context).userDao()
        val user = user.value as User

        return dao.getById(user.uid.toInt()).isNotEmpty()
    }

    fun saveUser(context: Context) {
        val dao = FavoriteDatabase.getInstance(context).userDao()
        val user = user.value as User

        if (dao.getById(user.uid.toInt()).isEmpty()) {
            dao.insert(user)
        }
    }

    fun getUser() = user

}