package com.codeiva.githubuserapp.ui.detail.follower

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.codeiva.githubuserapp.databinding.FragmentFollowerBinding
import com.codeiva.githubuserapp.model.User
import kotlinx.coroutines.InternalCoroutinesApi

/**
 * @author farhan
 * created at at 14:33 on 01/09/2020.
 */
@InternalCoroutinesApi
class FollowerFragment : Fragment() {

    private var followers = arrayListOf<User>()

    private lateinit var viewModel: FollowerViewModel
    private lateinit var binding: FragmentFollowerBinding
    private lateinit var adapter: FollowerAdapter

    companion object {
        fun newInstance(user: User?): Fragment {
            val args = Bundle()
            args.putParcelable("user", user)
            val f = FollowerFragment()
            f.arguments = args
            return f
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowerBinding.inflate(inflater, container, false)
        context ?: return binding.root

        val user: User = arguments?.getParcelable<User>("user") as User

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowerViewModel::class.java)
        showFollowers(user.username)

        return binding.root
    }

    private fun showFollowers(username: String) {
        showLoading(true)
        followers.clear()
        viewModel.setFollower(username)
        setViewModel()
    }

    private fun setViewModel() {
        viewModel.getUserData().observe(requireActivity(), Observer { result ->
            showLoading(true)
            if (result != null) {
                followers = result
                adapter = FollowerAdapter()
                binding.recyclerViewFollowers.adapter = adapter
                adapter.submitList(followers)
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
            binding.progressBarFollowers.visibility = View.VISIBLE
        } else {
            binding.progressBarFollowers.visibility = View.GONE
        }
    }
}