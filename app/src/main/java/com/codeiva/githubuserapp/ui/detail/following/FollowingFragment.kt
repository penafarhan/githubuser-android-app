package com.codeiva.githubuserapp.ui.detail.following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.codeiva.githubuserapp.databinding.FragmentFollowingBinding
import com.codeiva.githubuserapp.model.User
import kotlinx.coroutines.InternalCoroutinesApi

/**
 * @author farhan
 * created at at 14:33 on 01/09/2020.
 */
@InternalCoroutinesApi
class FollowingFragment : Fragment() {

    private var followings = arrayListOf<User>()

    private lateinit var viewModel: FollowingViewModel
    private lateinit var binding: FragmentFollowingBinding
    private lateinit var adapter: FollowingAdapter

    companion object {
        fun newInstance(user: User?): Fragment {
            val args = Bundle()
            args.putParcelable("user", user)
            val f = FollowingFragment()
            f.arguments = args
            return f
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        context ?: return binding.root

        val user: User = arguments?.getParcelable<User>("user") as User

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowingViewModel::class.java)
        showFollowings(user.username)

        return binding.root
    }

    private fun showFollowings(username: String) {
        showLoading(true)
        followings.clear()
        viewModel.setFollowing(username)
        setViewModel()
    }

    private fun setViewModel() {
        viewModel.getUserData().observe(requireActivity(), Observer { result ->
            showLoading(true)
            if (result != null) {
                followings = result
                adapter = FollowingAdapter()
                binding.recyclerViewFollowings.adapter = adapter
                adapter.submitList(followings)
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
            binding.imageViewEmpty.visibility = View.VISIBLE
            binding.textViewEmpty.visibility = View.VISIBLE
        } else {
            binding.imageViewEmpty.visibility = View.GONE
            binding.imageViewEmpty.visibility = View.GONE
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBarFollowings.visibility = View.VISIBLE
        } else {
            binding.progressBarFollowings.visibility = View.GONE
        }
    }
}