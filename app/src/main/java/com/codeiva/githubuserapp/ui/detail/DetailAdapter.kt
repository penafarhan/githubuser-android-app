package com.codeiva.githubuserapp.ui.detail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.codeiva.githubuserapp.model.User
import com.codeiva.githubuserapp.ui.detail.detail.DetailFragment
import com.codeiva.githubuserapp.ui.detail.follower.FollowerFragment
import com.codeiva.githubuserapp.ui.detail.following.FollowingFragment
import kotlinx.coroutines.InternalCoroutinesApi

/**
 * @author farhan
 * created at at 15:03 on 01/09/2020.
 */
@InternalCoroutinesApi
class DetailAdapter(activity: FragmentActivity, private val user: User) :
    FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> DetailFragment.newInstance(user)
            1 -> FollowerFragment.newInstance(user)
            2 -> FollowingFragment.newInstance(user)
            else -> DetailFragment.newInstance(user)
        }
    }
}