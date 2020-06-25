package com.dhimas.githubsuserfinder.data.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dhimas.githubsuserfinder.R
import com.dhimas.githubsuserfinder.data.model.UserAdapter.UserViewHolder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_user.view.*

class UserAdapter : RecyclerView.Adapter<UserViewHolder>() {
    private var listUser = ArrayList<User>()
    private var onUserClickCallback: OnUserClickCallback? = null

    fun setListUser(users: ArrayList<User>) {
        listUser.clear()
        listUser.addAll(users)
    }

    fun clearUser() {
        listUser.clear()
    }

    fun setOnUserClickCallback(onUserClickCallback: OnUserClickCallback) {
        this.onUserClickCallback = onUserClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int = listUser.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {
            with(itemView) {
                Picasso.get()
                    .load(user.avatarUrl)
                    .resize(60, 60)
                    .placeholder(R.drawable.octocat1)
                    .into(iv_avatar)
                tv_username.text = user.username

                itemView.setOnClickListener {
                    onUserClickCallback?.onUserClicked(user)
                }
            }
        }
    }

    interface OnUserClickCallback {
        fun onUserClicked(user: User)
    }

}