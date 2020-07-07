package com.dhimas.githubsuserfinder.database

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.dhimas.githubsuserfinder.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM User")
    fun cursorGetAll(): Cursor

    @Query("SELECT * FROM User WHERE uid = :id")
    fun cursorGetById(id: String): Cursor

    @Query("SELECT * FROM User")
    fun getAll(): List<User>

    @Query("SELECT * FROM User WHERE uid = :id")
    fun getById(id: Int): List<User>

    @Query("DELETE FROM User WHERE uid = :id")
    fun deleteById(id: String)

    @Insert
    fun insert(vararg user: User)

    @Delete
    fun delete(user: User)
}