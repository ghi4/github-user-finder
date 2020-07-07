package com.dhimas.githubsuserfinder.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhimas.githubsuserfinder.R
import com.dhimas.githubsuserfinder.model.User
import com.dhimas.githubsuserfinder.model.UserAdapter
import com.dhimas.githubsuserfinder.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), UserAdapter.OnUserClickCallback {
    private lateinit var viewModel: MainViewModel
    private lateinit var userAdapter: UserAdapter

    companion object {
        const val KEY_USERNAME: String = "KEY_USERNAME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(MainViewModel::class.java)

        setupUI()
        viewModelObserver()
    }

    private fun setupUI() {
        userAdapter = UserAdapter()

        tv_no_user.visibility = View.GONE
        rv_searchResult.setHasFixedSize(true)
        rv_searchResult.layoutManager = LinearLayoutManager(this)
        rv_searchResult.adapter = userAdapter

        userAdapter.setOnUserClickCallback(this)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    progressBar.visibility = View.VISIBLE
                    tv_no_user.visibility = View.GONE
                    viewModel.setKeywordPressed(query)
                }
                return false
            }

            override fun onQueryTextChange(keyword: String?): Boolean {
                if (!keyword.isNullOrEmpty()) {
                    progressBar.visibility = View.VISIBLE
                    tv_no_user.visibility = View.GONE

                    showOctocat(viewModel.getBoolOctocat().value!!)
                    viewModel.setBoolOctocatFalse()

                    viewModel.setKeyword(keyword)
                } else progressBar.visibility = View.GONE
                return false
            }
        })
    }

    private fun viewModelObserver() {
        viewModel.getUsers().observe(this, Observer { users ->
            if (users.isNotEmpty()) {
                userAdapter.notifyDataSetChanged()
                rv_searchResult.scheduleLayoutAnimation()

                tv_no_user.visibility = View.GONE
                userAdapter.setListUser(users)
                progressBar.visibility = View.GONE
            } else {
                progressBar.visibility = View.GONE
                userAdapter.notifyDataSetChanged()
                userAdapter.clearUser()
                tv_no_user.visibility = View.VISIBLE
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_change_settings) {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }
        if (item.itemId == R.id.action_open_favorite) {
            val intent = Intent(this, FavoriteActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onUserClicked(user: User) {
        val intent = Intent(this, UserDetailActivity::class.java)
        intent.putExtra(KEY_USERNAME, user.username)
        startActivity(intent)
    }

    private fun showOctocat(bool: Boolean) {

    }
}