package com.dhimas.githubsuserfinder

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhimas.githubsuserfinder.data.model.User
import com.dhimas.githubsuserfinder.data.model.UserAdapter
import com.dhimas.githubsuserfinder.userdetail.UserDetailActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener,
    UserAdapter.OnUserClickCallback {
    private lateinit var viewModel: MainViewModel
    private lateinit var userAdapter: UserAdapter
    private var boolOctocat: Boolean = true

    companion object {
        const val KEY_USERNAME: String = "KEY_USERNAME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        setupUI()
        viewModelObserver()

    }

    private fun setupUI() {
        userAdapter = UserAdapter()

        rv_searchResult.setHasFixedSize(true)
        rv_searchResult.layoutManager = LinearLayoutManager(this)
        rv_searchResult.adapter = userAdapter

        searchView.setOnQueryTextListener(this)

        userAdapter.setOnUserClickCallback(this)
    }

    private fun viewModelObserver() {
        viewModel.getUsers().observe(this, Observer { users ->
            if (users != null) {
                userAdapter.notifyDataSetChanged()
                rv_searchResult.scheduleLayoutAnimation()

                userAdapter.setListUser(users)
                progressBar.visibility = View.GONE
            } else progressBar.visibility = View.GONE
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_change_settings) {
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextSubmit(keyword: String?): Boolean {
        if (keyword != null) {
            progressBar.visibility = View.VISIBLE
            viewModel.setKeywordPressed(keyword)
        }
        return false
    }

    override fun onQueryTextChange(keyword: String?): Boolean {
        if (!keyword.isNullOrEmpty()) {
            showOctocat(boolOctocat)
            progressBar.visibility = View.VISIBLE

            viewModel.setKeyword(keyword)
        } else progressBar.visibility = View.GONE

        return false
    }

    override fun onUserClicked(user: User) {
        val intent = Intent(this, UserDetailActivity::class.java)
        intent.putExtra(KEY_USERNAME, user.username)
        startActivity(intent)
    }

    private fun showOctocat(boolean: Boolean) {
        if (boolean) {
            val anim = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_out)
            anim.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {
                    //Nothing to do
                }

                override fun onAnimationEnd(animation: Animation?) {
                    imageView.visibility = View.GONE
                }

                override fun onAnimationStart(animation: Animation?) {
                    //Nothing to do
                }

            })
            imageView.startAnimation(anim)
            boolOctocat = false
        }
    }
}