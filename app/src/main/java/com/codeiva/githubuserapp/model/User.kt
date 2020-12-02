package com.codeiva.githubuserapp.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * @author farhan
 * created at at 11:27 on 24/06/2020.
 */
@Entity(tableName = "users")
@Parcelize
data class User(
    @PrimaryKey
    @Expose
    @ColumnInfo(name = "id")
    @field:SerializedName("id") var id: Int,
    @Expose
    @ColumnInfo(name = "username")
    @field:SerializedName("login") var username: String,
    @Expose
    @ColumnInfo(name = "name")
    @field:SerializedName("name") var name: String?,
    @Expose
    @ColumnInfo(name = "company")
    @field:SerializedName("company") var company: String?,
    @Expose
    @ColumnInfo(name = "location")
    @field:SerializedName("location") var location: String?,
    @Expose
    @ColumnInfo(name = "bio")
    @field:SerializedName("bio") var bio: String?,
    @Expose
    @ColumnInfo(name = "repositories")
    @field:SerializedName("public_repos") var repositories: String?,
    @Expose
    @ColumnInfo(name = "followers")
    @field:SerializedName("followers") var followers: String?,
    @Expose
    @ColumnInfo(name = "following")
    @field:SerializedName("following") var following: String?,
    @Expose
    @ColumnInfo(name = "followersUrl")
    @field:SerializedName("followers_url") var followersUrl: String?,
    @Expose
    @ColumnInfo(name = "followingUrl")
    @field:SerializedName("following_url") var followingUrl: String?,
    @Expose
    @ColumnInfo(name = "avatar")
    @field:SerializedName("avatar_url") var avatar: String?
) : Parcelable