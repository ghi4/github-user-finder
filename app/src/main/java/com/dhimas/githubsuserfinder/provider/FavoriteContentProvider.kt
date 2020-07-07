package com.dhimas.githubsuserfinder.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.dhimas.githubsuserfinder.database.FavoriteDatabase
import com.dhimas.githubsuserfinder.database.UserDao

class FavoriteContentProvider : ContentProvider() {
    companion object {
        private const val FAVORITE = 1
        private const val FAVORITE_ID = 2
        private const val AUTHORITY = "com.dhimas.githubsuserfinder"
        private const val TABLE_NAME = "User"

        private lateinit var userDao: UserDao

        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init{
            //without ID
            //content://com.dhimasgithubsuserfinder/User
            uriMatcher.addURI(AUTHORITY, TABLE_NAME, FAVORITE)

            //with ID
            //content://com.dhimasgithubsuserfinder/User/ID
            uriMatcher.addURI(AUTHORITY, TABLE_NAME, FAVORITE_ID)
        }

    }

    override fun onCreate(): Boolean {
        userDao = FavoriteDatabase.getInstance(
            context as Context
        ).userDao()
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {

        return when(uriMatcher.match(uri)){
            FAVORITE -> userDao.cursorGetAll()
            FAVORITE_ID -> userDao.cursorGetById(uri.lastPathSegment.toString())
            else -> null
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Implement this to handle requests to insert a new row.")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return when(FAVORITE_ID){
            uriMatcher.match(uri) -> {
                userDao.deleteById(uri.lastPathSegment.toString())
                return 1
            }
            else -> 0
        }
    }

    override fun getType(uri: Uri): String? = null

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int = 0




}
