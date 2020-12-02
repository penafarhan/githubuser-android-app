package com.codeiva.githubuserapp.data.repository

import androidx.lifecycle.LiveData
import com.codeiva.githubuserapp.data.dao.FavoriteUserDao
import com.codeiva.githubuserapp.model.User

/**
 * @author farhan
 * created at at 8:20 on 22/09/2020.
 */
class FavoriteUserRepository(private val dao: FavoriteUserDao) {
    val favoriteUsers: LiveData<List<User>> = dao.getAll()

    fun remove(favoriteUser: User) {
        dao.delete(favoriteUser)
    }

    fun add(favoriteUser: User) {
        dao.insert(favoriteUser)
    }
}