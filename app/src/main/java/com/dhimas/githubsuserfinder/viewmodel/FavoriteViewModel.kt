package com.dhimas.githubsuserfinder.viewmodel

import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import android.os.HandlerThread
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dhimas.githubsuserfinder.helper.DatabaseContract.FavoriteUserColumn.Companion.CONTENT_URI
import com.dhimas.githubsuserfinder.helper.MappingHelper
import com.dhimas.githubsuserfinder.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteViewModel : ViewModel() {
    private var listUser: MutableLiveData<ArrayList<User>>? = null

    fun getUsers(context: Context): MutableLiveData<ArrayList<User>> {
        if (listUser == null) {
            listUser = MutableLiveData()
            loadUserMain(context)
        }
        return listUser as MutableLiveData<ArrayList<User>>
    }

    private fun loadUserMain(context: Context) {
        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        //Load for first-time
        loadUser(context)

        //Load for data change
        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                loadUserAsync(context)
            }
        }

        context.contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)
    }

    fun loadUserAsync(context: Context) {
        GlobalScope.launch(Dispatchers.Main) {
            val deferredFavorite = async(Dispatchers.IO) {
                val cursor = context.contentResolver?.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }

            val favoriteUsers = deferredFavorite.await()

            listUser!!.postValue(favoriteUsers)
        }
    }

    private fun loadUser(context: Context) {
        val cursor = context.contentResolver?.query(CONTENT_URI, null, null, null, null)
        val users = MappingHelper.mapCursorToArrayList(cursor)
        listUser!!.postValue(users)
    }

    fun deleteUser(context: Context, user: User) {
        val uriWithId = Uri.parse(CONTENT_URI.toString() + "/${user.uid}")
        context.contentResolver.delete(uriWithId, null, null)
    }
}