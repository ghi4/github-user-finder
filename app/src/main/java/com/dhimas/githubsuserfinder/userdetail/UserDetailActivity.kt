package com.dhimas.githubsuserfinder.userdetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dhimas.githubsuserfinder.MainActivity.Companion.KEY_USERNAME
import com.dhimas.githubsuserfinder.R
import com.dhimas.githubsuserfinder.data.model.User
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_user_detail.*

class UserDetailActivity : AppCompatActivity() {
    private lateinit var viewModel: UserDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        val username = intent.getStringExtra(KEY_USERNAME)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(UserDetailViewModel::class.java)

        if(username != null) {
            val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, username)
            viewPager.adapter = sectionPagerAdapter
            tabs.setupWithViewPager(viewPager)

            supportActionBar?.elevation = 0f

            viewModel.setUsername(username)

            viewModel.getUser().observe(this, Observer{ user ->
                loadToView(user)
            })
        }
    }

    fun loadToView(user: User){
            Picasso.get()
                .load(user.avatarUrl)
                .into(iv_avatar)
            tv_name.text = user.name
            tv_username.text = user.username
    }
}