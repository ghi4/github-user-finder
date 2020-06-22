package com.dhimas.githubsuserfinder.userdetail

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.dhimas.githubsuserfinder.R
import com.dhimas.githubsuserfinder.userdetail.fragment.FollowFragment

class SectionPagerAdapter(private val mContext: Context, fm: FragmentManager, val username: String): FragmentPagerAdapter(fm) {



    @StringRes
    private val TAB_TITLES = intArrayOf(
        R.string.follower,
        R.string.following
    )

    override fun getItem(position: Int): Fragment {

        var fragment: Fragment? = null
        when(position){
            0 -> fragment = FollowFragment.newInstance(0, username)
            1 -> fragment = FollowFragment.newInstance(1, username)
        }
        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int = 2

}