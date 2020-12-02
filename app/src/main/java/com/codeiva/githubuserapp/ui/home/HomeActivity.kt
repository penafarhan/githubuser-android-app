package com.codeiva.githubuserapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.codeiva.githubuserapp.App
import com.codeiva.githubuserapp.R
import com.codeiva.githubuserapp.model.User
import com.codeiva.githubuserapp.ui.detail.DetailActivity
import com.codeiva.githubuserapp.ui.favorite.FavoriteUserActivity
import com.codeiva.githubuserapp.ui.settings.SettingsActivity
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.InternalCoroutinesApi
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author farhan
 * created at at 10:54 on 24/06/2020.
 */
@InternalCoroutinesApi
class HomeActivity : AppCompatActivity() {

    private var users: ArrayList<User> = ArrayList()

    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: HomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        sayGreeting()

        showLoading(false)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(HomeViewModel::class.java)

        val toolbar = findViewById<Toolbar>(R.id.toolbar_home)
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""

        searchUser()
        setViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_item_favorite -> {
                val intent = Intent(this, FavoriteUserActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.menu_item_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            android.R.id.home -> {
                finish()
                true
            }
            else -> true
        }
    }

    private fun sayGreeting() {
        val calendar = Calendar.getInstance()
        when (calendar[Calendar.HOUR_OF_DAY]) {
            in 4..9 -> {
                imageView_homeHeader.setImageResource(R.drawable.img_sky_morning)
            }
            in 10..13 -> {
                imageView_homeHeader.setImageResource(R.drawable.img_sky_afternoon)
            }
            in 14..17 -> {
                imageView_homeHeader.setImageResource(R.drawable.img_sky_without_sun)
            }
            else -> {
                imageView_homeHeader.setImageResource(R.drawable.img_sky_night)
            }
        }
    }

    private fun searchUser() {
        searchView_home.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    if (query.isNotEmpty()) {
                        Log.d("SEARCH_QUERY", query)
                        users.clear()
                        viewModel.setUser(query)
                        setViewModel()
                        isEmpty(false)
                    }
                }
                showLoading(true)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })
    }

    private fun setViewModel() {
        viewModel.getUsers().observe(this, Observer { result ->
            showLoading(true)
            if (result != null) {
                users = result
                adapter = HomeAdapter()
                recyclerView_users.adapter = adapter
                adapter.submitList(users)
                isEmpty(false)
                showLoading(false)
            } else {
                isEmpty(true)
                showLoading(false)
            }
        })
    }

    private fun isEmpty(state: Boolean) {
        if (state) {
            imageView_empty.visibility = View.VISIBLE
            textView_empty.visibility = View.VISIBLE
        } else {
            imageView_empty.visibility = View.GONE
            textView_empty.visibility = View.GONE
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar_home.visibility = View.VISIBLE
        } else {
            progressBar_home.visibility = View.GONE
        }
    }
}