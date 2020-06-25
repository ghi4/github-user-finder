package com.dhimas.githubsuserfinder.userdetail.followers

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhimas.githubsuserfinder.R
import com.dhimas.githubsuserfinder.data.model.UserAdapter
import com.dhimas.githubsuserfinder.userdetail.following.FollowingFragment
import kotlinx.android.synthetic.main.activity_user_detail.*
import kotlinx.android.synthetic.main.followers_fragment.*

class FollowersFragment : Fragment() {

    companion object {
        private lateinit var viewModel: FollowViewModel
        private var userAdapter = UserAdapter()
        private var username = ""

        fun newInstance(username: String): FollowersFragment {
            val fragment = FollowersFragment()
            this.username = username

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

        userAdapter.clearUser()
        viewModel = ViewModelProvider(this).get(FollowViewModel::class.java)

        setupUI()
        viewModelObserver()
    }

    private fun setupUI(){
        rv_followers.setHasFixedSize(true)
        rv_followers.layoutManager = LinearLayoutManager(context)
        rv_followers.adapter = userAdapter

        progressBarFollowers.visibility = View.VISIBLE
        linear_info.visibility = View.GONE
    }

    private fun viewModelObserver(){
        viewModel.getListUser(username).observe(viewLifecycleOwner, Observer { users ->
            val newTitle = activity!!.resources.getString(R.string.follower) + "(${users.size})"
            activity!!.tabs.getTabAt(0)!!.text = newTitle

            if (users.isNotEmpty()) {
                linear_info.visibility = View.GONE

                userAdapter.setListUser(users)
                userAdapter.notifyDataSetChanged()

                rv_followers.scheduleLayoutAnimation()
                progressBarFollowers.visibility = View.GONE

            }else{
                progressBarFollowers.visibility = View.GONE
                linear_info.visibility = View.VISIBLE
            }
        })
    }

}