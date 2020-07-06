package com.dhimas.githubsuserfinder.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dhimas.githubsuserfinder.data.model.User

@Database(entities = [User::class], version = 1)
abstract class FavoriteDatabase : RoomDatabase() {
    companion object {
        @Volatile
        private var INSTANCE: FavoriteDatabase? = null
        private const val DB_NAME = "favorite-db"

        fun getInstance(context: Context?): FavoriteDatabase {
            return INSTANCE
                ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context!!.applicationContext,
                        FavoriteDatabase::class.java,
                        DB_NAME
                    )
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                    instance
                }
        }
    }

    abstract fun userDao(): UserDao
}