package com.dhimas.favoriteconsumer.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dhimas.favoriteconsumer.model.User

class FavoriteViewModel : ViewModel() {
    private var listUser: MutableLiveData<ArrayList<User>>? = null


    fun getUsers(context: Context): MutableLiveData<ArrayList<User>> {
        if (listUser == null) {
            listUser = MutableLiveData()
        }
        return listUser as MutableLiveData<ArrayList<User>>
    }
}