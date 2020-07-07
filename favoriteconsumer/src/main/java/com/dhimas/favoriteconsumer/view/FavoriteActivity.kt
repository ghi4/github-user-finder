package com.dhimas.favoriteconsumer.view

import android.content.Intent
import android.database.ContentObserver
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhimas.favoriteconsumer.R
import com.dhimas.favoriteconsumer.helper.MappingHelper
import com.dhimas.favoriteconsumer.model.User
import com.dhimas.favoriteconsumer.model.UserAdapter
import com.dhimas.favoriteconsumer.viewmodel.FavoriteViewModel
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity(), UserAdapter.OnUserClickCallback,
    UserAdapter.OnDeleteClickCallback {
    private lateinit var contentUri: Uri
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var userAdapter: UserAdapter

    companion object {
        const val KEY_USERNAME: String = "KEY_USERNAME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        //viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
        //    .get(FavoriteViewModel::class.java)

        contentUri = Uri.Builder().scheme("content")
            .authority("com.dhimas.githubsuserfinder")
            .appendPath("User")
            .build()

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler){
            override fun onChange(self: Boolean){
                Log.d("Info", "1")
                loadUserAsync()
            }
        }

        Log.d("Info", "2")
        contentResolver.registerContentObserver(contentUri, true, myObserver)

        loadUserAsync()



        //setupUI()
        //viewModelObserver()
    }

    private fun setupUI() {
        userAdapter = UserAdapter()

        rv_favorite.setHasFixedSize(true)
        rv_favorite.layoutManager = LinearLayoutManager(applicationContext)
        rv_favorite.adapter = userAdapter

        //userAdapter.setDeleteVisible()
        userAdapter.setOnUserClickCallback(this)
        userAdapter.setOnDeleteClickCallback(this)

        tv_no_favorite.visibility = View.GONE
    }

    fun loadUserAsync(){
        GlobalScope.launch(Dispatchers.Main) {
            val deferredFavorite = async(Dispatchers.IO) {
                val cursor = contentResolver?.query(contentUri, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }

            val favoriteUsers = deferredFavorite.await()

        }
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
//        viewModel.deleteUser(this, user)
        Toast.makeText(this, "${user.username} deleted.", Toast.LENGTH_SHORT).show()
    }
}
