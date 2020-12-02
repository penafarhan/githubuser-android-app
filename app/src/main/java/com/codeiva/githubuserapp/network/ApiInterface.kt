package com.codeiva.githubuserapp.network

import com.codeiva.githubuserapp.model.UserResponse
import com.codeiva.githubuserapp.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @author farhan
 * created at at 10:07 on 25/07/2020.
 */
interface ApiInterface {
    @GET("search/users")
    @Headers("Authorization: token 9e2d9fc27ddef10c6f0fe8ee232f6a7523ab7ef0")
    fun search(
        @Query("q") username: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 9e2d9fc27ddef10c6f0fe8ee232f6a7523ab7ef0")
    fun getDetail(
        @Path("username") username: String
    ): Call<User>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 9e2d9fc27ddef10c6f0fe8ee232f6a7523ab7ef0")
    fun getFollowers(
        @Path("username") username: String
    ): Call<Array<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 9e2d9fc27ddef10c6f0fe8ee232f6a7523ab7ef0")
    fun getFollowing(
        @Path("username") username: String
    ): Call<Array<User>>
}