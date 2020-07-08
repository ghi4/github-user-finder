package com.dhimas.githubsuserfinder.helper

import android.content.ContentValues
import android.database.Cursor
import androidx.core.content.contentValuesOf
import com.dhimas.githubsuserfinder.model.User
import com.dhimas.githubsuserfinder.helper.DatabaseContract.FavoriteUserColumn.Companion as favColumn

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

    fun mapValueToUser(values: ContentValues?): User? {
        if (values != null) {
            return User(
                values.getAsLong(favColumn.uid),
                values.getAsString(favColumn.username),
                values.getAsString(favColumn.name),
                values.getAsString(favColumn.avatarUrl),
                values.getAsString(favColumn.company),
                values.getAsString(favColumn.location),
                values.getAsString(favColumn.followersCount),
                values.getAsString(favColumn.followingCount),
                values.getAsString(favColumn.repoCount)
            )
        }
        return null
    }

    fun mapUserToValues(user: User): ContentValues {
        val values = contentValuesOf()
        values.put(favColumn.uid, user.uid)
        values.put(favColumn.username, user.username)
        values.put(favColumn.name, user.name)
        values.put(favColumn.avatarUrl, user.avatarUrl)
        values.put(favColumn.company, user.company)
        values.put(favColumn.location, user.location)
        values.put(favColumn.followersCount, user.followersCount)
        values.put(favColumn.followingCount, user.followingCount)
        values.put(favColumn.repoCount, user.repoCount)
        return values
    }
}