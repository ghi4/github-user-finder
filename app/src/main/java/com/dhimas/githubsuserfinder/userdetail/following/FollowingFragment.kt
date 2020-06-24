package com.dhimas.githubsuserfinder.userdetail.following

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhimas.githubsuserfinder.R
import com.dhimas.githubsuserfinder.data.model.UserAdapter
import kotlinx.android.synthetic.main.activity_user_detail.*
import kotlinx.android.synthetic.main.following_fragment.*

class FollowingFragment : Fragment() {

    companion object {
        private lateinit var viewModel: FollowingViewModel
        private var userAdapter = UserAdapter()
        private var username = ""
        private const val ARG_USERNAME = "username"

        fun newInstance(username: String): FollowingFragment {
            val fragment = FollowingFragment()
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
        return inflater.inflate(R.layout.following_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FollowingViewModel::class.java)
        viewModel.setUsername(username, "following")

        if (arguments != null) {
            username = arguments?.getString(ARG_USERNAME) as String
        }

        rv_following.setHasFixedSize(true)
        rv_following.layoutManager = LinearLayoutManager(context)
        rv_following.adapter = userAdapter

        progressBarFollowing.visibility = View.VISIBLE

        viewModel.getListUser().observe(viewLifecycleOwner, Observer { users ->
            if (users != null) {
                userAdapter.setUser(users)

                val newTitle = activity!!.resources.getString(R.string.following) + "(${users.size})"
                activity!!.tabs.getTabAt(1)!!.text = newTitle

                userAdapter.notifyDataSetChanged()
                rv_following.scheduleLayoutAnimation()
                progressBarFollowing.visibility = View.GONE
            }
        })
    }

}