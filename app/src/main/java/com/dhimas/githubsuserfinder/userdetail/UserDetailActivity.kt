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
    var username: String = ""
    var list: ArrayList<User>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        username = intent.getStringExtra(KEY_USERNAME)

        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, username)
        viewPager.adapter = sectionPagerAdapter
        tabs.setupWithViewPager(viewPager)

        supportActionBar?.elevation = 0f

        apiLoadTest()
    }

    fun apiLoadTest(){
        val service = RetrofitFactory.makeRetrofitService()
        val call = service.getUserDetail(username)
        call.enqueue(object : Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {

            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                val response = response.body()
                if(response != null){
                    Picasso.get()
                        .load(response.avatar_url)
                        .into(iv_avatar)
                    tv_name.text = response.name
                    tv_username.text = response.username
                }
            }
        })
    }
}