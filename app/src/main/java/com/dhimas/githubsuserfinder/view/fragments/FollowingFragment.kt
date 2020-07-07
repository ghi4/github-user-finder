package com.dhimas.githubsuserfinder.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhimas.githubsuserfinder.R
import com.dhimas.githubsuserfinder.model.UserAdapter
import com.dhimas.githubsuserfinder.viewmodel.FollowingViewModel
import kotlinx.android.synthetic.main.following_fragment.*

class FollowingFragment : Fragment() {
    private lateinit var viewModel: FollowingViewModel
    private lateinit var userAdapter: UserAdapter

    companion object {
        private var username = ""

        fun newInstance(username: String): FollowingFragment {
            val fragment =
                FollowingFragment()
            Companion.username = username

            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.following_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        userAdapter = UserAdapter()
        userAdapter.clearUser()
        viewModel = ViewModelProvider(this).get(FollowingViewModel::class.java)

        setupUI()
        viewModelObserver()
    }

    private fun setupUI() {
        rv_following.setHasFixedSize(true)
        rv_following.layoutManager = LinearLayoutManager(context)
        rv_following.adapter = userAdapter

        progressBarFollowing.visibility = View.VISIBLE
        tv_follow.visibility = View.GONE
    }

    private fun viewModelObserver() {
        viewModel.getListUser(username).observe(viewLifecycleOwner, Observer { users ->
            if (users.isNotEmpty()) {
                tv_follow.visibility = View.GONE

                userAdapter.setListUser(users)
                userAdapter.notifyDataSetChanged()

                rv_following.scheduleLayoutAnimation()
                progressBarFollowing.visibility = View.GONE
            } else {
                progressBarFollowing.visibility = View.GONE
                tv_follow.visibility = View.VISIBLE
            }
        })
    }

}