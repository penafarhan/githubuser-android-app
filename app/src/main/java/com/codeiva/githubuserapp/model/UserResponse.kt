package com.codeiva.githubuserapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * @author farhan
 * created at at 14:21 on 25/07/2020.
 */
data class UserResponse(
    @Expose
    @SerializedName("total_count") val totalCount: Int,
    @Expose
    @SerializedName("incomplete_results") val incompleteResults: Boolean,
    @Expose
    @SerializedName("items") val users: ArrayList<User>
) {
}