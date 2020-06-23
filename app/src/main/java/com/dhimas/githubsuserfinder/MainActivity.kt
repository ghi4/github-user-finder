package com.dhimas.githubsuserfinder

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhimas.githubsuserfinder.data.model.User
import com.dhimas.githubsuserfinder.data.model.UserAdapter
import com.dhimas.githubsuserfinder.userdetail.UserDetailActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private var listUser = ArrayList<User>()
    private var userAdapter = UserAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userAdapter.notifyDataSetChanged()

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
                userAdapter.setUser(listUser)
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