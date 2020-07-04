package com.dhimas.githubsuserfinder.data

import android.provider.BaseColumns

class UserContract {
    internal class UserColumn: BaseColumns{
        companion object {
            const val TABLE_NAME = "favorite_user"
            const val _ID = "_id"
            const val USERNAME = "username"
            const val NAME = "name"
            const val AVATAR_URL = "avatar_url"
            const val COMPANY = "company"
            const val LOCATION = "location"
            const val FOLLOWER_COUNT = "follower_count"
            const val FOLLOWING_COUNT = "following_count"
            const val REPOSITORY_COUNT = "repository_count"
        }
    }
}