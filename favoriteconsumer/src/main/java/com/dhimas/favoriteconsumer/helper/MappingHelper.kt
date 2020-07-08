package com.dhimas.favoriteconsumer.helper

import android.database.Cursor
import com.dhimas.favoriteconsumer.model.User
import com.dhimas.favoriteconsumer.helper.DatabaseContract.FavoriteUserColumn.Companion as favColumn

object MappingHelper {

    fun mapCursorToArrayList(cursor: Cursor?): ArrayList<User> {
        val favoriteList = ArrayList<User>()

        cursor?.apply {
            while (moveToNext()) {
                val uid = getInt(getColumnIndexOrThrow(favColumn.uid))
                val username = getString(getColumnIndexOrThrow(favColumn.username))
                val name = getString(getColumnIndexOrThrow(favColumn.name))
                val avatarUrl = getString(getColumnIndexOrThrow(favColumn.avatarUrl))
                val company = getString(getColumnIndexOrThrow(favColumn.company))
                val location = getString(getColumnIndexOrThrow(favColumn.location))
                val followersCount = getString(getColumnIndexOrThrow(favColumn.followersCount))
                val followingCount = getString(getColumnIndexOrThrow(favColumn.followingCount))
                val repoCount = getString(getColumnIndexOrThrow(favColumn.repoCount))

                favoriteList.add(
                    User(
                        uid.toLong(), username, name, avatarUrl, company, location,
                        followersCount, followingCount, repoCount
                    )
                )
            }
        }
        return favoriteList
    }

}