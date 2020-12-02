package com.codeiva.app.consumerapp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author farhan
 * created at at 14:48 on 22/09/2020.
 */
@Parcelize
data class User(
    var id: Int,
    var username: String,
    var name: String?,
    var company: String?,
    var location: String?,
    var bio: String?,
    var repositories: String?,
    var followers: String?,
    var following: String?,
    var followersUrl: String?,
    var followingUrl: String?,
    var avatar: String?
) : Parcelable