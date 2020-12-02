package com.codeiva.githubuserapp.ui.detail.follower

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codeiva.githubuserapp.App
import com.codeiva.githubuserapp.model.User
import com.codeiva.githubuserapp.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author farhan
 * created at at 15:50 on 01/09/2020.
 */
@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class FollowerViewModel : ViewModel() {
    val liveDataUser = MutableLiveData<ArrayList<User>>()
    val list = ArrayList<User>()

    fun getUserData(): MutableLiveData<ArrayList<User>> {
        return liveDataUser
    }

    fun setFollower(username: String) {
        val follower = ApiClient.service.getFollowers(username)
        follower.enqueue(object : Callback<Array<User>> {
            override fun onResponse(call: Call<Array<User>>, response: Response<Array<User>>) {
                if (response.isSuccessful) {
                    val userList = response.body()!!
                    if (userList.isNotEmpty()) {
                        for (i in 0 until userList.size) {
                            getUserDetailData(userList[i].username)
                        }
                    }
                } else {
                    Toast.makeText(App.getContext(), response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Array<User>>, t: Throwable) {
                Toast.makeText(App.getContext(), t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getUserDetailData(username: String) {
        val userDetail = ApiClient.service.getDetail(username)
        userDetail.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                response.body()?.let {
                    list.add(it)
                    liveDataUser.postValue(list)
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(App.getContext(), t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}