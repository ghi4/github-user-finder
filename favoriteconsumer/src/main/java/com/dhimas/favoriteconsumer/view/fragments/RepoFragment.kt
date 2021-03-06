package com.dhimas.favoriteconsumer.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhimas.favoriteconsumer.R
import com.dhimas.favoriteconsumer.model.RepoAdapter
import com.dhimas.favoriteconsumer.viewmodel.RepoViewModel
import kotlinx.android.synthetic.main.repo_fragment.*

class RepoFragment : Fragment() {
    private lateinit var viewModel: RepoViewModel
    private lateinit var repoAdapter: RepoAdapter

    companion object {
        private var username = ""
        fun newInstance(username: String): RepoFragment {
            val fragment =
                RepoFragment()
            Companion.username = username

            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.repo_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        repoAdapter = RepoAdapter(activity)
        repoAdapter.clearRepo()
        viewModel = ViewModelProvider(this).get(RepoViewModel::class.java)

        setupUI()
        viewModelObserver()
    }

    private fun setupUI() {
        rv_repo.setHasFixedSize(true)
        rv_repo.layoutManager = LinearLayoutManager(context)
        rv_repo.adapter = repoAdapter
        rv_repo.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL))

        progressBarRepo.visibility = View.VISIBLE
        tv_repo.visibility = View.GONE
    }

    private fun viewModelObserver() {
        viewModel.getListRepo(username).observe(viewLifecycleOwner, Observer { repos ->
            if (repos.isNotEmpty()) {
                tv_repo.visibility = View.GONE

                repoAdapter.setListRepo(repos)
                repoAdapter.notifyDataSetChanged()

                rv_repo.scheduleLayoutAnimation()
                progressBarRepo.visibility = View.GONE
            } else {
                progressBarRepo.visibility = View.GONE
                tv_repo.visibility = View.VISIBLE
            }
        })
    }

}