package com.dhimas.githubsuserfinder.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dhimas.githubsuserfinder.R
import com.dhimas.githubsuserfinder.model.User
import com.dhimas.githubsuserfinder.view.MainActivity.Companion.KEY_USERNAME
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

        //Set fab not favorite icon
        fab_favorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)

        fab_favorite.setOnClickListener {
            if (viewModel.isUserFavorite(this)) {
                val message = getString(R.string.already_in_favorite)
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            } else {
                //Add user to favorite list
                viewModel.saveUser(this)

                //Set fab favorite icon
                fab_favorite.setImageResource(R.drawable.ic_baseline_favorite_24)

                val message = "$username " + getString(R.string.added_to_favorite)
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun viewModelObserver() {
        viewModel.getUser().observe(this, Observer { user ->
            loadToView(user)
        })
    }

    private fun loadToView(user: User) {
        val targetWidth = 120
        val targetHeight = 120
        Picasso.get()
            .load(user.avatarUrl)
            .placeholder(R.drawable.octocat1)
            .error(R.drawable.avatar_eror)
            .resize(targetWidth, targetHeight)
            .into(iv_avatar)
        tv_name.text = user.name?.trim() ?: "-"
        tv_username.text = user.username?.trim() ?: "-"
        tv_company.text = user.company?.trim() ?: "-"
        tv_location.text = user.location?.trim() ?: "-"

        if (viewModel.isUserFavorite(this))
            fab_favorite.setImageResource(R.drawable.ic_baseline_favorite_24)

        tabs.getTabAt(0)!!.text = getString(R.string.follower) + "\n(${user.followersCount})"
        tabs.getTabAt(1)!!.text = getString(R.string.following) + "\n(${user.followingCount})"
        tabs.getTabAt(2)!!.text = getString(R.string.repository) + "\n(${user.repoCount})"
    }
}