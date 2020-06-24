package com.dhimas.githubsuserfinder.userdetail.followers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhimas.githubsuserfinder.R
import com.dhimas.githubsuserfinder.data.model.UserAdapter
import kotlinx.android.synthetic.main.activity_user_detail.*
import kotlinx.android.synthetic.main.followers_fragment.*

class FollowersFragment : Fragment() {

    companion object {
        private lateinit var viewModel: FollowViewModel
        private var userAdapter = UserAdapter()
        private var username = ""
        private const val ARG_USERNAME = "username"

        fun newInstance(username: String): FollowersFragment {
            val fragment = FollowersFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle

            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.followers_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FollowViewModel::class.java)
        viewModel.setUsername(username, "followers")

        if (arguments != null) {
            username = arguments?.getString(ARG_USERNAME) as String
        }

        rv_followers.setHasFixedSize(true)
        rv_followers.layoutManager = LinearLayoutManager(context)
        rv_followers.adapter = userAdapter

        progressBarFollowers.visibility = View.VISIBLE



        viewModel.getListUser().observe(viewLifecycleOwner, Observer { users ->
            if (users != null) {
                userAdapter.setUser(users)

                val newTitle = activity!!.resources.getString(R.string.follower) + "(${users.size})"
                activity!!.tabs.getTabAt(0)!!.text = newTitle

                userAdapter.notifyDataSetChanged()
                rv_followers.scheduleLayoutAnimation()
                progressBarFollowers.visibility = View.GONE
            }
        })
    }
}