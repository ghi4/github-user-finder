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
import com.dhimas.githubsuserfinder.data.model.User
import com.dhimas.githubsuserfinder.data.model.UserAdapter
import com.dhimas.githubsuserfinder.database.FavoriteDatabase
import com.dhimas.githubsuserfinder.viewmodel.FavoriteViewModel
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity : AppCompatActivity(), UserAdapter.OnUserClickCallback, UserAdapter.OnDeleteClickCallback {
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var userAdapter: UserAdapter

    companion object {
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

    private fun viewModelObserver() {
        viewModel.getUsers(this).observe(this, Observer { users ->
            if (users.isNotEmpty()) {
                tv_no_favorite.visibility = View.GONE

                userAdapter.notifyDataSetChanged()
                rv_favorite.scheduleLayoutAnimation()

                userAdapter.setListUser(users)
            }else{
                tv_no_favorite.visibility = View.VISIBLE
            }
        })
    }

    private fun setupUI() {
        userAdapter = UserAdapter()

        rv_favorite.setHasFixedSize(true)
        rv_favorite.layoutManager = LinearLayoutManager(applicationContext)
        rv_favorite.adapter = userAdapter

        tv_no_favorite.visibility = View.GONE

        userAdapter.setDeleteVisible()
        userAdapter.setOnUserClickCallback(this)
        userAdapter.setOnDeleteClickCallback(this)

    }

    override fun onUserClicked(user: User) {
        val intent = Intent(this, UserDetailActivity::class.java)
        intent.putExtra(KEY_USERNAME, user.username)
        startActivity(intent)
    }

    override fun onDeleteClicked(user: User) {
        viewModel.deleteUser(this, user)
        Toast.makeText(this, "${user.username} deleted.", Toast.LENGTH_LONG).show()
    }
}
