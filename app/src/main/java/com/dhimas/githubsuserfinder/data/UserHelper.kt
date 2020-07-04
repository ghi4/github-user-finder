package com.dhimas.githubsuserfinder.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.dhimas.githubsuserfinder.data.UserContract.UserColumn.Companion.TABLE_NAME

class UserHelper(context: Context) {
    private var databaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object{
        private const val DATABASE_TABLE = TABLE_NAME
        private var INSTANCE: UserHelper? = null

    }
}