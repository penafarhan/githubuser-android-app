package com.codeiva.githubuserapp.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.codeiva.githubuserapp.App
import com.codeiva.githubuserapp.data.AppDatabase
import com.codeiva.githubuserapp.data.dao.FavoriteUserDao
import com.codeiva.githubuserapp.data.repository.FavoriteUserRepository
import com.codeiva.githubuserapp.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Job

/**
 * @author farhan
 * created at at 8:58 on 22/09/2020.
 */
@InternalCoroutinesApi
class FavoriteUserViewModel() : ViewModel() {
    private val repository: FavoriteUserRepository
    private val dao: FavoriteUserDao = AppDatabase.getInstance(App.getContext()).favoriteUserDao()

    private var mFavoriteUsers: LiveData<List<User>>

    val favoriteUsers: LiveData<List<User>>
        get() = mFavoriteUsers

    private var job = Job()
    private var scope = CoroutineScope(Dispatchers.IO + job)

    init {
        repository = FavoriteUserRepository(dao)
        mFavoriteUsers = repository.favoriteUsers
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}