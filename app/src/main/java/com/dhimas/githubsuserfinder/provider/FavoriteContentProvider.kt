package com.dhimas.githubsuserfinder.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import com.dhimas.githubsuserfinder.database.FavoriteDatabase
import com.dhimas.githubsuserfinder.database.UserDao

class FavoriteContentProvider : ContentProvider() {
    companion object {
        private lateinit var userDao: UserDao
    }

    override fun onCreate(): Boolean {
        userDao = FavoriteDatabase.getInstance(
            context as Context
        ).userDao()
        return true
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        userDao.deleteById(uri.lastPathSegment.toString().toLong())
        return 0
    }

    override fun getType(uri: Uri): String? {
        TODO(
            "Implement this to handle requests for the MIME type of the data" +
                    "at the given URI"
        )
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Implement this to handle requests to insert a new row.")
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return userDao.cursorGetAll()
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        TODO("Implement this to handle requests to update one or more rows.")
    }
}
