package com.dhimas.githubsuserfinder

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhimas.githubsuserfinder.data.model.User
import com.dhimas.githubsuserfinder.data.model.UserAdapter
import com.dhimas.githubsuserfinder.userdetail.UserDetailActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener,
    UserAdapter.OnItemClickCallback {
    private lateinit var viewModel: MainViewModel
    private var userAdapter = UserAdapter()
    private var textChangeTimer = Timer()

    companion object {
        const val KEY_USERNAME: String = "KEY_USERNAME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        setupUI()
        viewModelObserver()
    }

    private fun setupUI(){
        rv_searchResult.setHasFixedSize(true)
        rv_searchResult.layoutManager = LinearLayoutManager(this)
        rv_searchResult.adapter = userAdapter

        searchView.setOnQueryTextListener(this)

        userAdapter.setOnItemClickCallback(this)
    }

    private fun viewModelObserver(){
        viewModel.getUsers().observe(this, Observer { users ->
            if(users != null){
                userAdapter.notifyDataSetChanged()
                rv_searchResult.scheduleLayoutAnimation()
                userAdapter.setListUser(users)
                progressBar.visibility = View.GONE
            }else progressBar.visibility = View.GONE
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_change_settings){
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextSubmit(keyword: String?): Boolean {
        if (keyword != null) {
            progressBar.visibility = View.VISIBLE
            viewModel.setKeyword(keyword)
        }
        return false
    }

    override fun onQueryTextChange(keyword: String?): Boolean {
        textChangeTimer.cancel()
        textChangeTimer = Timer()

        if(!keyword.isNullOrEmpty()){
            progressBar.visibility = View.VISIBLE
            textChangeTimer.schedule(500){
                viewModel.setKeyword(keyword)
            }
        }else progressBar.visibility = View.GONE

        return false
    }

    override fun onItemClicked(user: User) {
        val intent = Intent(this, UserDetailActivity::class.java)
        intent.putExtra(KEY_USERNAME, user.username)
        startActivity(intent)
    }
}