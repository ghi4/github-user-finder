package com.dhimas.githubsuserfinder.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dhimas.githubsuserfinder.data.model.User
import com.dhimas.githubsuserfinder.database.FavoriteDatabase

class FavoriteViewModel : ViewModel() {
    private var listUser: MutableLiveData<ArrayList<User>>? = null

    private fun loadData(context: Context) {
        val dao = FavoriteDatabase.getInstance(context).userDao()
        listUser?.postValue(dao.getAll() as ArrayList<User>?)
    }

    fun getUsers(context: Context): MutableLiveData<ArrayList<User>> {
        if (listUser == null) {
            listUser = MutableLiveData()
            loadData(context)
        }
        return listUser as MutableLiveData<ArrayList<User>>
    }
}