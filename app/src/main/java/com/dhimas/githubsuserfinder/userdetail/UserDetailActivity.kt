package com.dhimas.githubsuserfinder.userdetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dhimas.githubsuserfinder.MainActivity.Companion.KEY_USERNAME
import com.dhimas.githubsuserfinder.R
import com.dhimas.githubsuserfinder.data.api.RetrofitFactory
import com.dhimas.githubsuserfinder.data.model.User
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_user_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        val username = intent.getStringExtra(KEY_USERNAME)

        if(username != null) {
            val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, username)
            viewPager.adapter = sectionPagerAdapter
            tabs.setupWithViewPager(viewPager)

            supportActionBar?.elevation = 0f

            apiLoadTest(username)
        }
    }

    fun apiLoadTest(username: String) {
        val service = RetrofitFactory.makeRetrofitService()
        val call = service.getUserDetail(username)
        call.enqueue(object : Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {

            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                val user = response.body()
                if (user != null) {
                    Picasso.get()
                        .load(user.avatarUrl)
                        .into(iv_avatar)
                    tv_name.text = user.name
                    tv_username.text = user.username
                }
            }
        })
    }
}