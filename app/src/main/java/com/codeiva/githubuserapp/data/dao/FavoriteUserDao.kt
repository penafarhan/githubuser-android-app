package com.codeiva.githubuserapp.data.dao

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Delete
import com.codeiva.githubuserapp.model.User

/**
 * @author farhan
 * created at at 8:19 on 22/09/2020.
 */
@Dao
interface FavoriteUserDao {
    @Query("SELECT * FROM users")
    fun getAll(): LiveData<List<User>>

    @Query("SELECT * FROM users WHERE username = :username")
    fun getByUserName(username: String): LiveData<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Delete
    fun delete(user: User)

    @Query("SELECT * FROM users")
    fun cursorGetAll(): Cursor
}