package com.dhimas.githubsuserfinder.userdetail.fragment

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
import kotlinx.android.synthetic.main.follow_fragment.*

class FollowFragment : Fragment() {

    companion object {
        private lateinit var viewModel: FollowViewModel
        private var userAdapter = UserAdapter()
        private var type = 0
        private var username = ""
        private const val ARG_TYPE = "type"
        private const val ARG_USERNAME = "username"

        fun newInstance(type: Int, username: String): FollowFragment {
            val fragment = FollowFragment()
            val bundle = Bundle()
            bundle.putInt(ARG_TYPE, type)
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle

            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.follow_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FollowViewModel::class.java)

        if (arguments != null) {
            type = arguments?.getInt(ARG_TYPE, 0) as Int
            username = arguments?.getString(ARG_USERNAME) as String
        }

        userAdapter = UserAdapter()
        userAdapter.notifyDataSetChanged()

        rv_follow.layoutManager = LinearLayoutManager(activity!!.applicationContext!!)
        rv_follow.adapter = userAdapter

        when (type) {
            0 -> viewModel.setUsername(username, "followers")
            1 -> viewModel.setUsername(username, "following")
        }

        viewModel.getUsers().observe(viewLifecycleOwner, Observer { users ->
            if (users != null) {
                Log.d("Info", "In observer: ${users.size} and ${users[0].username} ")
                userAdapter.setUser(users)
            }
        })
    }
}