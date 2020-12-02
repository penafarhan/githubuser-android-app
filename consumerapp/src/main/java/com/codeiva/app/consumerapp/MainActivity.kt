package com.codeiva.app.consumerapp

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getFavoriteUsers()
    }

    private fun setAdapter(cursor: Cursor?) {
        recyclerView_favoriteUser.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = MainAdapter(convertCursor(cursor), this@MainActivity)
        }
    }

    private fun setEmptyIllustration(state: Boolean) {
        if (state) {
            imageView_empty.visibility = View.VISIBLE
            textView_empty.visibility = View.VISIBLE
        } else {
            imageView_empty.visibility = View.GONE
            textView_empty.visibility = View.GONE
        }
    }

    private fun getFavoriteUsers() {
        val table = "users"
        val authority = "com.codeiva.githubuserapp.provider"

        val uri: Uri = Uri.Builder()
            .scheme("content")
            .authority(authority)
            .appendPath(table)
            .build()

        val contentResolver = this.contentResolver
        val cursor = contentResolver.query(
            uri,
            null,
            null,
            null,
            null
        )

        if (cursor != null && cursor.count > 0) {
            setEmptyIllustration(false)
            setAdapter(cursor)
        } else {
            setEmptyIllustration(true)
            setAdapter(cursor)
        }

    }

    private fun convertCursor(cursor: Cursor?): ArrayList<User> {
        val users = ArrayList<User>()

        cursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow("id"))
                val username = getString(getColumnIndexOrThrow("username"))
                val name = getString(getColumnIndexOrThrow("name"))
                val company = getString(getColumnIndexOrThrow("company"))
                val location = getString(getColumnIndexOrThrow("location"))
                val bio = getString(getColumnIndexOrThrow("bio"))
                val repositories = getString(getColumnIndexOrThrow("repositories"))
                val followers = getString(getColumnIndexOrThrow("followers"))
                val following = getString(getColumnIndexOrThrow("following"))
                val followersUrl = getString(getColumnIndexOrThrow("followersUrl"))
                val followingUrl = getString(getColumnIndexOrThrow("followingUrl"))
                val avatar = getString(getColumnIndexOrThrow("avatar"))
                users.add(
                    User(
                        id,
                        username,
                        name,
                        company,
                        location,
                        bio,
                        repositories,
                        followers,
                        following,
                        followersUrl,
                        followingUrl,
                        avatar
                    )
                )
            }
        }
        return users
    }
}
