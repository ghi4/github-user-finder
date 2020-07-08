package com.dhimas.githubsuserfinder.helper

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {
    private const val SCHEME = "content"
    const val AUTHORITY = "com.dhimas.githubsuserfinder"

    class FavoriteUserColumn : BaseColumns {
        companion object {
            const val TABLE_NAME = "User"
            const val uid = "uid"
            const val username = "username"
            const val name = "name"
            const val avatarUrl = "avatarUrl"
            const val company = "company"
            const val location = "location"
            const val followersCount = "followersCount"
            const val followingCount = "followingCount"
            const val repoCount = "repoCount"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}