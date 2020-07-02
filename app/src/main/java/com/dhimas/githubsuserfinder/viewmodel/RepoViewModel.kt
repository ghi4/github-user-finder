package com.dhimas.githubsuserfinder.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dhimas.githubsuserfinder.data.api.RetrofitFactory
import com.dhimas.githubsuserfinder.data.model.Repo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepoViewModel : ViewModel() {
    private var listRepo: MutableLiveData<ArrayList<Repo>>? = null

    fun getListRepo(username: String): MutableLiveData<ArrayList<Repo>> {
        if (listRepo == null) {
            listRepo = MutableLiveData()
            loadData(username)
        }

        return listRepo as MutableLiveData<ArrayList<Repo>>
    }

    private fun loadData(username: String) {
        val service = RetrofitFactory.makeRetrofitService()
        val call = service.getUserRepo(username)

        call.enqueue(object : Callback<ArrayList<Repo>> {
            override fun onFailure(call: Call<ArrayList<Repo>>, t: Throwable) {
                Log.d("Throwable", t.message.toString())
            }

            override fun onResponse(
                call: Call<ArrayList<Repo>>,
                response: Response<ArrayList<Repo>>
            ) {
                if (response.isSuccessful) {
                    listRepo?.postValue(response.body() as ArrayList<Repo>)
                }
            }
        })

    }
}