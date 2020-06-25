package com.dhimas.githubsuserfinder.userdetail.following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

        fun newInstance(username: String): FollowingFragment {
            val fragment = FollowingFragment()
            this.username = username

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

        userAdapter.clearUser()
        viewModel = ViewModelProvider(this).get(FollowingViewModel::class.java)

        setupUI()
        viewModelObserver()
    }

    private fun setupUI(){
        rv_following.setHasFixedSize(true)
        rv_following.layoutManager = LinearLayoutManager(context)
        rv_following.adapter = userAdapter

        progressBarFollowing.visibility = View.VISIBLE
        linear_info.visibility = View.GONE
    }

    private fun viewModelObserver(){
        viewModel.getListUser(username).observe(viewLifecycleOwner, Observer { users ->
            val newTitle = activity!!.resources.getString(R.string.following) + "(${users.size})"
            activity!!.tabs.getTabAt(1)!!.text = newTitle

            if (users.isNotEmpty()) {
                linear_info.visibility = View.GONE

                userAdapter.setListUser(users)
                userAdapter.notifyDataSetChanged()

                rv_following.scheduleLayoutAnimation()
                progressBarFollowing.visibility = View.GONE
            }else{
                progressBarFollowing.visibility = View.GONE
                linear_info.visibility = View.VISIBLE
            }
        })
    }

}