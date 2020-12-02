package com.codeiva.githubuserapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.codeiva.githubuserapp.data.dao.FavoriteUserDao
import com.codeiva.githubuserapp.model.User
import kotlinx.coroutines.InternalCoroutinesApi

/**
 * @author farhan
 * created at at 8:22 on 22/09/2020.
 */
@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteUserDao(): FavoriteUserDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        @InternalCoroutinesApi
        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var mInstance = instance

                if (mInstance == null) {
                    mInstance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "githubuserapp_db"
                    ).fallbackToDestructiveMigration().build()
                    instance = mInstance
                }
                return mInstance
            }
        }
    }
}