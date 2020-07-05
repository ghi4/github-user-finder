package com.dhimas.githubsuserfinder.data

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.dhimas.githubsuserfinder.data.model.User

class DatabaseProvider: ContentProvider() {
    val AUTHORITY = "com.dhimas.githubsuserfinder.provider"
    val URI_FAVORITE = Uri.parse("content://$AUTHORITY/" + User::class.java.simpleName)
    val MATCHER = UriMatcher(UriMatcher.NO_MATCH)

    override fun onCreate(): Boolean {
        return true
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
       return Uri.parse("halo")
    }

    override fun query(
        p0: Uri,
        p1: Array<out String>?,
        p2: String?,
        p3: Array<out String>?,
        p4: String?
    ): Cursor? {
        TODO("Not yet implemented")
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        return 0
    }

    override fun delete(uri: Uri, p1: String?, p2: Array<out String>?): Int {
        if(context != null){
            FavoriteDatabase.getInstance(context!!).userDao().deleteById(ContentUris.parseId(uri))
            context!!.contentResolver.notifyChange(uri, null)
            return 1
        }
        return 0
    }

    override fun getType(p0: Uri): String? {
        return "da"
    }

}