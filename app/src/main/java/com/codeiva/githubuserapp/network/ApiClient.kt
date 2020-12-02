package com.codeiva.githubuserapp.network

import com.codeiva.githubuserapp.util.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author farhan
 * created at at 9:58 on 25/07/2020.
 */
class ApiClient {
    companion object {
        private var retrofit: Retrofit? = null
        val service: ApiInterface
            get() {
                if (retrofit == null) {
                    retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                }

                return retrofit!!.create<ApiInterface>(ApiInterface::class.java)
            }
    }
}