package com.dhimas.githubsuserfinder.provider

import android.appwidget.AppWidgetManager
import android.content.*
import android.database.Cursor
import android.net.Uri
import com.dhimas.githubsuserfinder.database.FavoriteDatabase
import com.dhimas.githubsuserfinder.database.UserDao
import com.dhimas.githubsuserfinder.helper.DatabaseContract.AUTHORITY
import com.dhimas.githubsuserfinder.helper.DatabaseContract.FavoriteUserColumn.Companion.CONTENT_URI
import com.dhimas.githubsuserfinder.helper.DatabaseContract.FavoriteUserColumn.Companion.TABLE_NAME
import com.dhimas.githubsuserfinder.helper.MappingHelper
import com.dhimas.githubsuserfinder.model.User
import com.dhimas.githubsuserfinder.widget.FavoriteStackWidget

class FavoriteContentProvider : ContentProvider() {
    companion object {
        private const val FAVORITE = 1
        private const val FAVORITE_ID = 2

        private lateinit var userDao: UserDao

        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            //without ID
            //content://com.dhimas.githubsuserfinder/User
            uriMatcher.addURI(AUTHORITY, TABLE_NAME, FAVORITE)

            //with ID
            //content://com.dhimas.githubsuserfinder/User/ID
            uriMatcher.addURI(AUTHORITY, "$TABLE_NAME/#", FAVORITE_ID)
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
        return when (uriMatcher.match(uri)) {
            FAVORITE -> userDao.cursorGetAll()
            FAVORITE_ID -> userDao.cursorGetById(uri.lastPathSegment.toString())
            else -> null
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        var added = 0
        when (uriMatcher.match(uri)) {
            FAVORITE -> {
                val user = MappingHelper.mapValueToUser(values) as User

                //Check if user in database or not
                if (userDao.getById(user.uid.toInt()).isEmpty()) {
                    userDao.insert(user)
                }
                added = 1
            }
        }

        notifyWidgetDataSetChanged()
        context?.contentResolver?.notifyChange(CONTENT_URI, null)

        return Uri.parse("$CONTENT_URI/$added")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return when (FAVORITE_ID) {
            uriMatcher.match(uri) -> {
                userDao.deleteById(uri.lastPathSegment.toString())

                notifyWidgetDataSetChanged()
                context?.contentResolver?.notifyChange(CONTENT_URI, null)

                return 1
            }
            else -> 0
        }
    }

    override fun getType(uri: Uri): String? = null

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        var updated = 0

        when (uriMatcher.match(uri)) {
            FAVORITE_ID -> {
                userDao.update(MappingHelper.mapValueToUser(values))
                updated = 1

                notifyWidgetDataSetChanged()
                context?.contentResolver?.notifyChange(CONTENT_URI, null)
            }
        }

        return updated
    }

    private fun notifyWidgetDataSetChanged() {
        val intent = Intent(context, FavoriteStackWidget::class.java)
        intent.action = "android.appwidget.action.APPWIDGET_UPDATE"
        val componentName =
            context?.applicationContext?.let { ComponentName(it, FavoriteStackWidget::class.java) }
        val ids = AppWidgetManager.getInstance(context?.applicationContext)
            .getAppWidgetIds(componentName)

        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
        context?.sendBroadcast(intent)
    }


}
