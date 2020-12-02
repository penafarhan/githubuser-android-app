package com.codeiva.githubuserapp.ui.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codeiva.githubuserapp.App
import com.codeiva.githubuserapp.databinding.ItemUserFavoriteBinding
import com.codeiva.githubuserapp.model.User
import com.codeiva.githubuserapp.ui.detail.DetailActivity
import kotlinx.coroutines.InternalCoroutinesApi

/**
 * @author farhan
 * created at at 8:58 on 22/09/2020.
 */
@InternalCoroutinesApi
class FavoriteUserAdapter : ListAdapter<User, FavoriteUserAdapter.ViewHolder>(
    DiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemUserFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.apply {
            bind(createOnClickListener(item), item)
        }
    }

    class ViewHolder(
        private val binding: ItemUserFavoriteBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(listener: View.OnClickListener, item: User) {
            binding.apply {
                user = item
                Glide.with(binding.root).load(item.avatar)
                    .into(binding.imageViewUserAvatar)
                clickListener = listener
                executePendingBindings()
            }
        }
    }

    private fun createOnClickListener(
        user: User
    ): View.OnClickListener {
        return View.OnClickListener {
            val intent = Intent(
                App.getContext(), DetailActivity::class.java
            )
            intent.putExtra(DetailActivity.ITEM, user)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            App.getContext().startActivity(intent)
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldUser: User, newUser: User): Boolean {
        return oldUser.id == newUser.id
    }

    override fun areContentsTheSame(oldUser: User, newUser: User): Boolean {
        return oldUser == newUser
    }

}