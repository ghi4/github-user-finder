package com.dhimas.githubsuserfinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhimas.githubsuserfinder.data.api.RetrofitFactory
import com.dhimas.githubsuserfinder.data.model.SearchResult
import com.dhimas.githubsuserfinder.data.model.User
import com.dhimas.githubsuserfinder.userdetail.UserDetailActivity
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private var list = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(keyword: String?): Boolean {
                if (keyword != null) {
                    loadApiTest(keyword)
                }
                return false
            }

            override fun onQueryTextChange(keyword: String?): Boolean {
                return false
            }
        })
    }

    fun loadApiTest(keyword: String){
        val service = RetrofitFactory.makeRetrofitService()
        val call: Call<SearchResult> = service.getSearchResult(keyword)
        call.enqueue(object : Callback<SearchResult>{
            override fun onFailure(call: Call<SearchResult>, t: Throwable) {
                Log.d("Info", "MainAct API: $t")
            }

            override fun onResponse(call: Call<SearchResult>, response: Response<SearchResult>) {
                val response = response.body()

                if (response != null) {
                    list = response.user as ArrayList<User>
                    loadOnRecycler()
                }
            }
        })
    }

    fun loadOnRecycler(){
        rv_searchResult.setHasFixedSize(true)

        rv_searchResult.layoutManager = LinearLayoutManager(this)
        val userAdapter = UserAdapter(list)
        rv_searchResult.adapter = userAdapter

        userAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback{
            override fun onItemClicked(user: User) {
                showUser(user)
            }

        })
    }

    fun showUser(user: User){
        val intent = Intent(this, UserDetailActivity::class.java)
        intent.putExtra(Companion.KEY_USERNAME, user.username)
        startActivity(intent)
    }

    companion object {
        const val KEY_USERNAME: String = "KEY_USERNAME"
    }


}