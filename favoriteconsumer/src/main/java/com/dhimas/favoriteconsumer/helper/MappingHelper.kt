package com.dhimas.favoriteconsumer.helper

import android.database.Cursor
import com.dhimas.favoriteconsumer.model.User

object MappingHelper {
    fun mapCursorToArrayList(cursor: Cursor?): ArrayList<User>{
        val favoriteList = ArrayList<User>()

        cursor?.apply {
            while (moveToNext()){
                val uid = getInt(getColumnIndexOrThrow("uid"))
                val username = getString(getColumnIndexOrThrow("username"))
                val name = getString(getColumnIndexOrThrow("name"))
                val avatarUrl = getString(getColumnIndexOrThrow("avatarUrl"))
                val company = getString(getColumnIndexOrThrow("company"))
                val location = getString(getColumnIndexOrThrow("location"))
                val followersCount = getString(getColumnIndexOrThrow("followersCount"))
                val followingCount = getString(getColumnIndexOrThrow("followingCount"))
                val repoCount = getString(getColumnIndexOrThrow("repoCount"))

                favoriteList.add(User(
                    uid.toLong(), username, name, avatarUrl, company, location,
                    followersCount, followingCount, repoCount))
            }
        }
        return favoriteList
    }
}