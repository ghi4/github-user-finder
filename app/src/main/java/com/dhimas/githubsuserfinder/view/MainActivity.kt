package com.dhimas.githubsuserfinder.view

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
import com.dhimas.githubsuserfinder.R
import com.dhimas.githubsuserfinder.data.model.User
import com.dhimas.githubsuserfinder.data.model.UserAdapter
import com.dhimas.githubsuserfinder.service.AlarmReceiver
import com.dhimas.githubsuserfinder.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener,
    UserAdapter.OnUserClickCallback {
    private lateinit var viewModel: MainViewModel
    private lateinit var userAdapter: UserAdapter

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

        val alarmReceiver = AlarmReceiver()
        alarmReceiver.setAlarm(this)

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
        if(item.itemId == R.id.open_favorite){
            val intent = Intent(this, FavoriteActivity::class.java)
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
            progressBar.visibility = View.VISIBLE

            showOctocat(viewModel.getBoolOctocat().value!!)
            viewModel.setBoolOctocatFalse()

            viewModel.setKeyword(keyword)
        } else progressBar.visibility = View.GONE

        return false
    }

    override fun onUserClicked(user: User) {
        val intent = Intent(this, UserDetailActivity::class.java)
        intent.putExtra(KEY_USERNAME, user.username)
        startActivity(intent)
    }

    private fun showOctocat(bool: Boolean){

    }
}