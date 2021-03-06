package com.dhimas.githubsuserfinder.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhimas.githubsuserfinder.R
import com.dhimas.githubsuserfinder.model.User
import com.dhimas.githubsuserfinder.model.UserAdapter
import com.dhimas.githubsuserfinder.viewmodel.FavoriteViewModel
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity : AppCompatActivity(), UserAdapter.OnUserClickCallback,
    UserAdapter.OnDeleteClickCallback {
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var userAdapter: UserAdapter

    companion object {
        //Key Intent to Detail Activity
        const val KEY_USERNAME: String = "KEY_USERNAME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(FavoriteViewModel::class.java)

        setupUI()
        viewModelObserver()
    }

    private fun setupUI() {
        userAdapter = UserAdapter()

        rv_favorite.setHasFixedSize(true)
        rv_favorite.layoutManager = LinearLayoutManager(applicationContext)
        rv_favorite.adapter = userAdapter

        userAdapter.setDeleteVisible()
        userAdapter.setOnUserClickCallback(this)
        userAdapter.setOnDeleteClickCallback(this)

        tv_no_favorite.visibility = View.GONE
    }

    private fun viewModelObserver() {
        viewModel.getUsers(this).observe(this, Observer { users ->
            if (users.isNotEmpty()) {
                userAdapter.setListUser(users)
                userAdapter.notifyDataSetChanged()

                tv_no_favorite.visibility = View.GONE
            } else {
                userAdapter.clearUser()
                userAdapter.notifyDataSetChanged()

                tv_no_favorite.visibility = View.VISIBLE
            }
        })
    }

    override fun onUserClicked(user: User) {
        val intent = Intent(this, UserDetailActivity::class.java)
        intent.putExtra(KEY_USERNAME, user.username)
        startActivity(intent)
    }

    override fun onDeleteClicked(user: User) {
        viewModel.deleteUser(this, user)
        Toast.makeText(this, "${user.username} deleted.", Toast.LENGTH_SHORT).show()
    }
}
