package com.codeiva.githubuserapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.codeiva.githubuserapp.App
import com.codeiva.githubuserapp.data.AppDatabase
import com.codeiva.githubuserapp.data.dao.FavoriteUserDao
import com.codeiva.githubuserapp.data.repository.FavoriteUserRepository
import com.codeiva.githubuserapp.model.User
import kotlinx.coroutines.*

/**
 * @author farhan
 * created at at 14:34 on 01/09/2020.
 */
@InternalCoroutinesApi
class DetailViewModel() : ViewModel() {
    private val repository: FavoriteUserRepository
    private val dao: FavoriteUserDao = AppDatabase.getInstance(App.getContext()).favoriteUserDao()

    private var favoriteUsers: LiveData<List<User>>

    private var job = Job()
    private var scope = CoroutineScope(Dispatchers.IO + job)

    init {
        repository = FavoriteUserRepository(dao)
        favoriteUsers = repository.favoriteUsers
    }

    fun addFavoriteUser(
        id: Int,
        name: String,
        username: String,
        company: String,
        location: String,
        bio: String,
        repositories: String,
        followers: String,
        following: String,
        followersUrl: String,
        followingUrl: String,
        avatar: String
    ) {
        scope.launch {
            repository.add(
                User(
                    id = id,
                    name = name,
                    username = username,
                    company = company,
                    location = location,
                    bio = bio,
                    repositories = repositories,
                    followers = followers,
                    following = following,
                    followersUrl = followersUrl,
                    followingUrl = followingUrl,
                    avatar = avatar
                )
            )
        }
    }

    fun deleteFavoriteUser(
        id: Int,
        username: String,
        name: String,
        company: String,
        location: String,
        bio: String,
        repositories: String,
        followers: String,
        following: String,
        followersUrl: String,
        followingUrl: String,
        avatar: String
    ) {
        scope.launch {
            repository.remove(
                User(
                    id = id,
                    username = username,
                    name = name,
                    company = company,
                    location = location,
                    bio = bio,
                    repositories = repositories,
                    followers = followers,
                    following = following,
                    followersUrl = followersUrl,
                    followingUrl = followingUrl,
                    avatar = avatar
                )
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}