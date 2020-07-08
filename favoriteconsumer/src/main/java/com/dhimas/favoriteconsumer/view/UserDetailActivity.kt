package com.dhimas.favoriteconsumer.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dhimas.favoriteconsumer.R
import com.dhimas.favoriteconsumer.model.User
import com.dhimas.favoriteconsumer.view.FavoriteActivity.Companion.KEY_USERNAME
import com.dhimas.favoriteconsumer.viewmodel.UserDetailViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_user_detail.*

class UserDetailActivity : AppCompatActivity() {
    private lateinit var viewModel: UserDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        val username = intent.getStringExtra(KEY_USERNAME) ?: ""

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(UserDetailViewModel::class.java)
        viewModel.setUsername(username)

        setupUI(username)
        viewModelObserver()
    }

    private fun setupUI(username: String) {
        val sectionPagerAdapter =
            SectionPagerAdapter(
                this,
                supportFragmentManager,
                username
            )
        viewPager.adapter = sectionPagerAdapter
        tabs.setupWithViewPager(viewPager)
        supportActionBar?.elevation = 0f
    }

    private fun viewModelObserver() {
        viewModel.getUser().observe(this, Observer { user ->
            loadToView(user)
        })
    }

    private fun loadToView(user: User) {
        Picasso.get()
            .load(user.avatarUrl)
            .placeholder(R.drawable.octocat1)
            .resize(120, 120)
            .into(iv_avatar)
        tv_name.text = user.name?.trim() ?: "-"
        tv_username.text = user.username?.trim() ?: "-"
        tv_company.text = user.company?.trim() ?: "-"
        tv_location.text = user.location?.trim() ?: "-"

        fab_favorite.visibility = View.GONE

        tabs.getTabAt(0)!!.text = getString(R.string.follower) + "\n(${user.followersCount})"
        tabs.getTabAt(1)!!.text = getString(R.string.following) + "\n(${user.followingCount})"
        tabs.getTabAt(2)!!.text = getString(R.string.repository) + "\n(${user.repoCount})"
    }

}