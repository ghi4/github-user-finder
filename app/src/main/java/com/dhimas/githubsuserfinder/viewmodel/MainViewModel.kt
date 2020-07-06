package com.dhimas.githubsuserfinder.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dhimas.githubsuserfinder.data.api.RetrofitFactory
import com.dhimas.githubsuserfinder.data.model.SearchResult
import com.dhimas.githubsuserfinder.data.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.concurrent.schedule

class MainViewModel : ViewModel() {
    private var listUsers = MutableLiveData<ArrayList<User>>()
    private var textChangeTimer = Timer()
    private var boolOctocat = MutableLiveData<Boolean>(true)

    fun setKeywordPressed(keyword: String) {
        textChangeTimer.cancel()
        loadData(keyword)
    }

    fun setKeyword(keyword: String) {
        textChangeTimer.cancel()
        textChangeTimer = Timer()
        textChangeTimer.schedule(500) {
            loadData(keyword)
        }
    }

    private fun loadData(keyword: String) {
        val service = RetrofitFactory.makeRetrofitService()
        val call = service.getSearchResult(keyword)
        call.enqueue(object : Callback<SearchResult> {
            override fun onFailure(call: Call<SearchResult>, t: Throwable) {
                Log.d("Throwable", t.message.toString())
            }

            override fun onResponse(
                call: Call<SearchResult>,
                response: Response<SearchResult>
            ) {
                if (response.isSuccessful) {
                    listUsers.postValue(response.body()?.user as ArrayList<User>)
                }
            }
        })
    }

    fun getUsers(): MutableLiveData<ArrayList<User>> {
        return listUsers
    }

    fun setBoolOctocatFalse() {
        boolOctocat.value = false
    }

    fun getBoolOctocat(): MutableLiveData<Boolean> {
        return boolOctocat
    }
}