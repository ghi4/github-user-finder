package com.dhimas.githubsuserfinder.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dhimas.githubsuserfinder.view.MainActivity.Companion.KEY_USERNAME
import com.dhimas.githubsuserfinder.R
import com.dhimas.githubsuserfinder.data.FavoriteDatabase
import com.dhimas.githubsuserfinder.data.model.User
import com.dhimas.githubsuserfinder.viewmodel.UserDetailViewModel
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

        Picasso.get()
            .load(R.drawable.octocat1)
            .resize(120, 120)
            .into(iv_avatar)
    }

    private fun viewModelObserver() {
        viewModel.getUser().observe(this, Observer { user ->
            loadToView(user)
        })
    }

    private fun loadToView(user: User) {
        saveUser(user)

        Picasso.get()
            .load(user.avatarUrl)
            .placeholder(R.drawable.octocat1)
            .resize(120, 120)
            .into(iv_avatar)
        tv_name.text = user.name
        tv_username.text = user.username
        tv_company.text = user.company
        tv_location.text = user.location

        if (user.name.isNullOrEmpty()) {
            tv_name.visibility = View.GONE
        }
        if (user.company.isNullOrEmpty()) {
            linear_company.visibility = View.GONE
        }
        if (user.location.isNullOrEmpty()) {
            linear_location.visibility = View.GONE
        }

        tabs.getTabAt(0)!!.text = getString(R.string.follower) + "\n(${user.followersCount})"
        tabs.getTabAt(1)!!.text = getString(R.string.following) + "\n(${user.followingCount})"
        tabs.getTabAt(2)!!.text = getString(R.string.repository) + "\n(${user.repoCount})"
    }

    fun saveUser(user: User){
        val database = FavoriteDatabase.getInstance(applicationContext)
        val dao = database.userDao()

        if(dao.getById(user.uid.toInt()).isEmpty()){
            dao.insert(user)
        }
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }

}