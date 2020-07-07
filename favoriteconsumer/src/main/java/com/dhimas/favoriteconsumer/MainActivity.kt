package com.dhimas.favoriteconsumer

import android.database.ContentObserver
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var contentUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
    }

    private fun loadUserAsync(){
        Log.d("Info", "3")
        GlobalScope.launch(Dispatchers.Main) {
            Log.d("Info", "4")
            val defferedFavorite = async(Dispatchers.IO) {
                Log.d("Info", "5")
                val cursor = contentResolver?.query(contentUri, null, null, null, null)
                    MappingHelper.mapCursorToArrayList(cursor)
            }

            Log.d("Info", "")
            val favoriteUsers = defferedFavorite.await()
            if(favoriteUsers.size > 0){
                Log.d("Cursor", "UWAW" + favoriteUsers.size)
                Log.d("Cursor", "UWAW" + favoriteUsers[0].avatarUrl)
            }else{
                Log.d("Cursor", "YACHHH")
            }
        }
    }
}
