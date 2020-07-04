package com.dhimas.githubsuserfinder.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhimas.githubsuserfinder.R
import com.dhimas.githubsuserfinder.data.FavoriteDatabase
import com.dhimas.githubsuserfinder.data.model.User
import com.dhimas.githubsuserfinder.data.model.UserAdapter
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.android.synthetic.main.followers_fragment.*

class FavoriteActivity : AppCompatActivity() {
    lateinit var userAdapter: UserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        userAdapter = UserAdapter()
        userAdapter.clearUser()

        getFavoriteUser()
        setupUI()

    }

    private fun setupUI(){
        rv_favorite.setHasFixedSize(true)
        rv_favorite.layoutManager = LinearLayoutManager(applicationContext)
        rv_favorite.adapter = userAdapter
    }


    private fun getFavoriteUser(){
        val db = FavoriteDatabase.getInstance(applicationContext)
        val dao = db.userDao()

        val listUser = arrayListOf<User>()
        listUser.addAll(dao.getAll())
        userAdapter.setListUser(listUser)

    }
}
