package com.codeiva.githubuserapp.ui.detail.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.codeiva.githubuserapp.databinding.FragmentDetailBinding
import com.codeiva.githubuserapp.model.User

/**
 * @author farhan
 * created at at 14:33 on 01/09/2020.
 */
class DetailFragment() : Fragment() {

    companion object {
        fun newInstance(user: User?): Fragment {
            val args = Bundle()
            args.putParcelable("user", user)
            val f = DetailFragment()
            f.arguments = args
            return f
        }
    }

    private lateinit var binding: FragmentDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        context ?: return binding.root

        val user: User = arguments?.getParcelable<User>("user") as User
        binding.apply {
            textViewUserRepository.text = user.repositories
            textViewUserFollower.text = user.followers
            textViewUserFollowing.text = user.following
            textViewUserUsername.text = user.username
            textViewUserLocation.text = user.location
            textViewUserCompany.text = user.company
        }

        return binding.root
    }
}