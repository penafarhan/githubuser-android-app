package com.codeiva.app.consumerapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_user.view.*


/**
 * @author farhan
 * created at at 14:49 on 22/09/2020.
 */
class MainAdapter(private val users: List<User>, private val context: Context) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    companion object {
        private const val message = "Sorry, You can open user detail only in Github User App"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(users[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {
            itemView.apply {
                textView_name.text = user.name
                textView_username.text = user.username
                Glide.with(itemView.context)
                    .load(user.avatar)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(imageView_userAvatar)

                setOnClickListener {
                    val userArray = ArrayList<String>()
                    userArray.add(user.username)
                    userArray.add(user.name ?: "")
                    userArray.add(user.company ?: "")
                    userArray.add(user.location ?: "")
                    userArray.add(user.bio ?: "")
                    userArray.add(user.repositories ?: "")
                    userArray.add(user.followers ?: "")
                    userArray.add(user.following ?: "")
                    userArray.add(user.followersUrl ?: "")
                    userArray.add(user.followingUrl ?: "")
                    userArray.add(user.avatar ?: "")

                    val intent: Intent? =
                        context.packageManager.getLaunchIntentForPackage("com.codeiva.githubuserapp")
                    if (intent != null) {
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        context.startActivity(intent)
                    } else {
                        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}