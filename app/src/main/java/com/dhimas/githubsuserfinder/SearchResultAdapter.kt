package com.dhimas.githubsuserfinder

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dhimas.githubsuserfinder.SearchResultAdapter.ViewHolder
import com.dhimas.githubsuserfinder.data.model.User
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_user.view.*

class SearchResultAdapter(private val listUser: ArrayList<User>): RecyclerView.Adapter<ViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback =onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listUser.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(user: User){
            with(itemView){
                Picasso.get()
                    .load(user.avatar_url)
                    .resize(60,60)
                    .into(iv_avatar)
                tv_username.text = user.username

                itemView.setOnClickListener{
                    onItemClickCallback?.onItemClicked(user)
                }
            }
        }
    }

    interface OnItemClickCallback{
        fun onItemClicked(user: User)
    }
}