package com.dhimas.githubsuserfinder

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhimas.githubsuserfinder.data.model.User
import com.dhimas.githubsuserfinder.data.model.UserAdapter
import com.dhimas.githubsuserfinder.userdetail.UserDetailActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private var userAdapter = UserAdapter()
    private var textChangeTimer = Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_searchResult.setHasFixedSize(true)
        rv_searchResult.layoutManager = LinearLayoutManager(this)
        rv_searchResult.adapter = userAdapter

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(keyword: String?): Boolean {
                if (keyword != null) {
                    viewModel.setKeyword(keyword)
                }
                return false
            }

            override fun onQueryTextChange(keyword: String?): Boolean {
                textChangeTimer.cancel()
                textChangeTimer = Timer()
                textChangeTimer.schedule(500){
                    if(!keyword.isNullOrEmpty()){
                        viewModel.setKeyword(keyword)
                    }
                }

                return false
            }
        })

        userAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(user: User) {
                showUser(user)
            }
        })

        viewModel.getUsers().observe(this, Observer { users ->
            if(users != null){
                Log.d("Info", "$users")
                userAdapter.notifyDataSetChanged()
                rv_searchResult.scheduleLayoutAnimation()
                userAdapter.setUser(users)
            }
        })
    }

    fun showUser(user: User) {
        val intent = Intent(this, UserDetailActivity::class.java)
        intent.putExtra(KEY_USERNAME, user.username)
        startActivity(intent)
    }

    companion object {
        const val KEY_USERNAME: String = "KEY_USERNAME"
    }
}