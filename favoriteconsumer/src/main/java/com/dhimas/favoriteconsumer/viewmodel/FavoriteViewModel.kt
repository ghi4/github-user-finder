package com.dhimas.favoriteconsumer.viewmodel

import android.content.Context
import android.database.ContentObserver
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dhimas.favoriteconsumer.helper.MappingHelper
import com.dhimas.favoriteconsumer.model.User
import com.dhimas.favoriteconsumer.view.FavoriteActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteViewModel : ViewModel() {
    private var listUser: MutableLiveData<ArrayList<User>>? = null

    fun getUsers(context: Context): MutableLiveData<ArrayList<User>> {
        if (listUser == null) {
            listUser = MutableLiveData()
            loadUserDirect(context)
        }
        return listUser as MutableLiveData<ArrayList<User>>
    }

    fun loadUserMain(context: Context){
        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        Log.d("Info", "1")

        val myObserver = object : ContentObserver(handler){
            override fun onChange(self: Boolean){
                Log.d("Info", "2")
                loadUserAsync(context)
            }
        }

        Log.d("Info", "3")
        context.contentResolver.registerContentObserver(FavoriteActivity.contentUri, true, myObserver)
    }

    fun loadUserAsync(context: Context){
        Log.d("Info", "5")
        GlobalScope.launch(Dispatchers.Main) {
            Log.d("Info", "6")
            val deferredFavorite = async(Dispatchers.IO) {
                Log.d("Info", "7")
                val cursor = context.contentResolver?.query(FavoriteActivity.contentUri, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }

            val favoriteUsers = deferredFavorite.await()
            Log.d("Info", "8: " + favoriteUsers.size )
            listUser!!.postValue(favoriteUsers)
        }
    }

    private fun loadUserDirect(context: Context) {
        val cursor = context.contentResolver?.query(FavoriteActivity.contentUri, null, null, null, null)
        listUser!!.postValue(MappingHelper.mapCursorToArrayList(cursor))
    }
}