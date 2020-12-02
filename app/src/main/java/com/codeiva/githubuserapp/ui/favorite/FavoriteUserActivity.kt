package com.codeiva.githubuserapp.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.codeiva.githubuserapp.R
import kotlinx.android.synthetic.main.activity_favorite_user.*
import kotlinx.coroutines.InternalCoroutinesApi

/**
 * @author farhan
 * created at at 8:40 on 22/09/2020.
 */
@InternalCoroutinesApi
class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var adapter: FavoriteUserAdapter
    private lateinit var viewModel: FavoriteUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_user)

        viewModel = ViewModelProvider(this).get(FavoriteUserViewModel::class.java)

        imageView_actionBack.setOnClickListener { onBackPressed() }

        setEmptyInterface(true)
        adapter = FavoriteUserAdapter()
        recyclerView_favoriteUser.adapter = adapter

        getFavoriteUsers()
    }

    private fun setEmptyInterface(state: Boolean) {
        if (state) {
            imageView_empty.visibility = View.VISIBLE
            textView_empty.visibility = View.VISIBLE
            recyclerView_favoriteUser.visibility = View.GONE
        } else {
            imageView_empty.visibility = View.GONE
            textView_empty.visibility = View.GONE
            recyclerView_favoriteUser.visibility = View.VISIBLE
        }
    }

    private fun getFavoriteUsers() {
        viewModel.favoriteUsers.observe(this, Observer { result ->
            if (result.isNotEmpty()) {
                setEmptyInterface(false)
                adapter.submitList(result)
            } else {
                setEmptyInterface(true)
            }
        })
    }
}