package com.dhimas.githubsuserfinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import com.dhimas.githubsuserfinder.data.api.RetrofitFactory
import com.dhimas.githubsuserfinder.data.model.SearchResult
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(keyword: String?): Boolean {
                if (keyword != null) {

                }
                return false
            }

            override fun onQueryTextChange(keyword: String?): Boolean {
                TODO("Not yet implemented")
            }
        })
    }

    fun loadApiTest(keyword: String){
        val service = RetrofitFactory.makeRetrofitService()
        val call: Call<SearchResult> = service.getSearchResult(keyword)
        call.enqueue(object : Callback<SearchResult>{
            override fun onFailure(call: Call<SearchResult>, t: Throwable) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call<SearchResult>, response: Response<SearchResult>) {
                TODO("Not yet implemented")
            }
        })
    }

    fun loadOnRecycler(){

    }


}