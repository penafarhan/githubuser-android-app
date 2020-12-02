package com.codeiva.githubuserapp.ui.detail

import android.os.Bundle
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.codeiva.githubuserapp.R
import com.codeiva.githubuserapp.data.AppDatabase
import com.codeiva.githubuserapp.databinding.ActivityDetailBinding
import com.codeiva.githubuserapp.model.User
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.InternalCoroutinesApi

/**
 * @author farhan
 * created at at 11:44 on 24/06/2020.
 */
@InternalCoroutinesApi
class DetailActivity : AppCompatActivity() {

    companion object {
        const val ITEM = "item_user"
    }

    private lateinit var user: User
    private lateinit var binding: ActivityDetailBinding
    private lateinit var adapter: DetailAdapter
    private lateinit var viewModel: DetailViewModel
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        binding.imageViewActionBack.setOnClickListener { onBackPressed() }

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        user = intent.getParcelableExtra(ITEM) as User

        binding.apply {
            Glide.with(this@DetailActivity).load(user.avatar).into(imageViewUserAvatar)
            textViewUserName.text = user.name
            textViewUserLocation.text = user.location
        }

        initViewPager()
        checkIsFavorite()
        setFloatingButtonListener()
    }

    private fun initViewPager() {
        adapter = DetailAdapter(this, user)
        binding.viewPagerDetail.adapter = adapter
        binding.viewPagerDetail.offscreenPageLimit = adapter.itemCount

        TabLayoutMediator(
            binding.tabLayoutDetail,
            binding.viewPagerDetail
        ) { tab, position ->
            when (position) {
                0 -> tab.text = resources.getString(R.string.page_detail)
                1 -> tab.text = resources.getString(R.string.page_follower)
                2 -> tab.text = resources.getString(R.string.page_following)
            }
        }.attach()

        val tabs = binding.tabLayoutDetail.getChildAt(0) as ViewGroup
        for (i in 0 until tabs.childCount) {
            val tab = tabs.getChildAt(i)
            val layoutParams = tab.layoutParams as LinearLayout.LayoutParams
            val dp16 = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 16f, resources.displayMetrics
            ).toInt()
            val dp8 = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 8f, resources.displayMetrics
            ).toInt()
            when (i) {
                0 -> {
                    layoutParams.marginStart = dp16
                    layoutParams.marginEnd = dp8
                }
                tabs.childCount - 1 -> {
                    layoutParams.marginStart = dp8
                    layoutParams.marginEnd = dp16
                }
                else -> {
                    layoutParams.marginStart = dp8
                    layoutParams.marginEnd = dp8
                }
            }
            tab.layoutParams = layoutParams
            tab.requestLayout()
        }
    }

    private fun checkIsFavorite() {
        val db = AppDatabase.getInstance(applicationContext)
        val dao = db.favoriteUserDao()
        dao.getByUserName(user.username).observe(this, Observer { result ->
            if (result.isNotEmpty() && result[0].username.isNotEmpty()) {
                isFavorite = true
                changeFabIcon(isFavorite)
            } else {
                isFavorite = false
                changeFabIcon(isFavorite)
            }
        })
    }

    private fun changeFabIcon(state: Boolean) {
        if (state) {
            floatingActionButton_actionAddFavoriteUser.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.ic_baseline_favorite_24
                )
            )
        } else {
            floatingActionButton_actionAddFavoriteUser.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.ic_baseline_favorite_border_24
                )
            )
        }
    }

    private fun setFloatingButtonListener() {
        floatingActionButton_actionAddFavoriteUser.setOnClickListener {
            checkIsFavorite()
            if (!isFavorite) {
                viewModel.addFavoriteUser(
                    id = user.id,
                    name = user.name ?: "",
                    username = user.username,
                    company = user.company ?: "",
                    bio = user.bio ?: "",
                    repositories = user.repositories ?: "",
                    location = user.location ?: "",
                    following = user.following ?: "",
                    followers = user.followers ?: "",
                    followingUrl = user.followingUrl ?: "",
                    followersUrl = user.followersUrl ?: "",
                    avatar = user.avatar ?: ""
                )
                Toast.makeText(this@DetailActivity, "Added to favorite", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.deleteFavoriteUser(
                    id = user.id,
                    name = user.name ?: "",
                    username = user.username,
                    company = user.company ?: "",
                    bio = user.bio ?: "",
                    repositories = user.repositories ?: "",
                    location = user.location ?: "",
                    following = user.following ?: "",
                    followers = user.followers ?: "",
                    followingUrl = user.followingUrl ?: "",
                    followersUrl = user.followersUrl ?: "",
                    avatar = user.avatar ?: ""
                )
                Toast.makeText(this@DetailActivity, "Removed from favorite", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}