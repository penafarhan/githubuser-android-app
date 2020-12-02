package com.codeiva.githubuserapp.ui.home

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codeiva.githubuserapp.App
import com.codeiva.githubuserapp.model.User
import com.codeiva.githubuserapp.model.UserResponse
import com.codeiva.githubuserapp.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author farhan
 * created at at 10:45 on 25/07/2020.
 */
@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class HomeViewModel : ViewModel() {
    val liveDataUsers = MutableLiveData<ArrayList<User>>()
    val users = ArrayList<User>()

    fun getUsers(): MutableLiveData<ArrayList<User>> {
        return liveDataUsers
    }

    fun setUser(searchQuery: String) {
        val search = ApiClient.service.search(searchQuery)
        search.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    val userList = response.body()!!.users
                    if (userList.size > 0) {
                        for (i in 0 until userList.size) {
                            getUserDetail(userList[i].username)
                        }
                    }
                } else {
                    Toast.makeText(App.getContext(), response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Toast.makeText(App.getContext(), t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getUserDetail(username: String) {
        val getDetail = ApiClient.service.getDetail(username)
        getDetail.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    users.add(user!!)
                    liveDataUsers.postValue(users)
                } else {
                    Toast.makeText(App.getContext(), response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(App.getContext(), t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}