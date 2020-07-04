package com.dhimas.githubsuserfinder.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

import com.dhimas.githubsuserfinder.data.UserContract.UserColumn
import com.dhimas.githubsuserfinder.data.UserContract.UserColumn.Companion.TABLE_NAME

internal class DatabaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object{
        private const val DATABASE_NAME = "dbfavoriteuser"
        private const val DATABASE_VERSION = 1

        private val SQL_CREATE_TABLE_FAVORITE = """
            CREATE TABLE $TABLE_NAME (
            ${UserColumn._ID} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${UserColumn.USERNAME} TEXT NOT NULL,
            ${UserColumn.NAME} TEXT NULL,
            ${UserColumn.AVATAR_URL} TEXT NOT NULL,
            ${UserColumn.COMPANY} TEXT NULL,
            ${UserColumn.LOCATION} TEXT NULL,
            ${UserColumn.FOLLOWER_COUNT} TEXT NULL,
            ${UserColumn.FOLLOWING_COUNT} TEXT NULL,
            ${UserColumn.REPOSITORY_COUNT} TEXT NULL
            )
        """.trimIndent()
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_FAVORITE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}