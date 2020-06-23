package com.dhimas.githubsuserfinder

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dhimas.githubsuserfinder.data.api.RetrofitFactory
import com.dhimas.githubsuserfinder.data.model.SearchResult
import com.dhimas.githubsuserfinder.data.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {
    private var listUsers = MutableLiveData<ArrayList<User>>()

    fun setKeyword(keyword: String) {
        val service = RetrofitFactory.makeRetrofitService()
        val call = service.getSearchResult(keyword)
        call.enqueue(object : Callback<SearchResult>{
            override fun onFailure(call: Call<SearchResult>, t: Throwable) {
                Log.d("Info", t.message.toString())
            }

            override fun onResponse(call: Call<SearchResult>, response: Response<SearchResult>) {
                if(response.isSuccessful){
                    listUsers.postValue(response.body()?.user as ArrayList<User>)
                }
            }
        })
    }

    fun getUsers(): MutableLiveData<ArrayList<User>> {
        Log.d("Info", "Yeay Call getUser")
        return listUsers
    }
}