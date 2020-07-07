package com.dhimas.favoriteconsumer.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.dhimas.favoriteconsumer.R
import kotlinx.android.synthetic.main.item_repo.view.*

class RepoAdapter(val ctx: FragmentActivity?) : RecyclerView.Adapter<RepoAdapter.RepoViewHolder>() {
    private var listRepo = ArrayList<Repo>()

    fun setListRepo(repos: ArrayList<Repo>) {
        listRepo.clear()
        listRepo.addAll(repos)
    }

    fun clearRepo() {
        listRepo.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_repo, parent, false)
        return RepoViewHolder(view)
    }

    override fun getItemCount(): Int = listRepo.size

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bind(listRepo[position])
    }

    inner class RepoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(repo: Repo) {
            with(itemView) {
                val noDesc = ctx!!.application.resources.getString(R.string.no_description)
                tv_repo_name.text = repo.name
                if (tv_repo_desc.text != null) {
                    tv_repo_desc.text = repo.description ?: noDesc
                }
            }
        }
    }
}

