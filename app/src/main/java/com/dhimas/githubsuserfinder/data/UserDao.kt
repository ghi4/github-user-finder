package com.dhimas.githubsuserfinder.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.dhimas.githubsuserfinder.data.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE uid = :id")
    fun getById(id: Int): List<User>

    @Insert
    fun insert(vararg user: User)

    @Delete
    fun delete(user: User)
}