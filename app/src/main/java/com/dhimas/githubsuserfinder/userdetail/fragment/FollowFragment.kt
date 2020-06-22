package com.dhimas.githubsuserfinder.userdetail.fragment

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhimas.githubsuserfinder.R
import com.dhimas.githubsuserfinder.UserAdapter
import com.dhimas.githubsuserfinder.data.api.RetrofitFactory
import com.dhimas.githubsuserfinder.data.model.SearchResult
import com.dhimas.githubsuserfinder.data.model.User
import com.dhimas.githubsuserfinder.userdetail.UserDetailActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_user_detail.*
import kotlinx.android.synthetic.main.follow_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowFragment : Fragment() {

    companion object {
        const val KEY_USERNAME: String = "KEY_USERNAME"
        private val ARG_TYPE = "type"
        private val ARG_USERNAME = "username"
        private var list = ArrayList<User>()

        fun newInstance(type: Int, username: String): FollowFragment{
            val fragment = FollowFragment()
            val bundle = Bundle()
            bundle.putInt(ARG_TYPE, type)
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle

            return fragment
        }
    }

    private lateinit var viewModel: FollowViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.follow_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FollowViewModel::class.java)

        var type = 0
        var username = ""
        if(getArguments() != null){
            type = arguments?.getInt(ARG_TYPE, 0) as Int
            username = arguments?.getString(ARG_USERNAME) as String
        }

        when(type){
            0 -> apiLoadTest(username, "followers")
            1 -> apiLoadTest(username, "following")
        }
    }

    fun apiLoadTest(username: String, follow: String){
        val service = RetrofitFactory.makeRetrofitService()
        val call = service.getUserFollow(username, follow)
        call.enqueue(object : Callback<ArrayList<User>> {
            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                Log.d("Info", "$t")
            }

            override fun onResponse(call: Call<ArrayList<User>>, response: Response<ArrayList<User>>) {
                if(response.isSuccessful){
                    val response = response.body() as ArrayList<User>
                    list = response
                    Log.d("Info", "List ${list.size}")
                    Log.d("Info", "$username")

                    loadOnRecycler()
                }
            }
        })
    }

    fun loadOnRecycler(){
        rv_follow.layoutManager = LinearLayoutManager(activity!!.applicationContext!!)
        val userAdapter = UserAdapter(list)
        rv_follow.adapter = userAdapter

        userAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback{
            override fun onItemClicked(user: User) {

            }

        })
    }

}