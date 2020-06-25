package com.dhimas.githubsuserfinder.userdetail

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.dhimas.githubsuserfinder.R
import com.dhimas.githubsuserfinder.userdetail.followers.FollowersFragment
import com.dhimas.githubsuserfinder.userdetail.following.FollowingFragment
import com.dhimas.githubsuserfinder.userdetail.repo.RepoFragment

class SectionPagerAdapter(
    private val mContext: Context,
    fm: FragmentManager,
    private val username: String
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    @StringRes
    private var tabTitle = intArrayOf(
        R.string.follower,
        R.string.following,
        R.string.repository
    )

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null

        when (position) {
            0 -> fragment = FollowersFragment.newInstance(username)
            1 -> fragment = FollowingFragment.newInstance(username)
            2 -> fragment = RepoFragment.newInstance(username)
        }
        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(tabTitle[position])
    }

    override fun getCount(): Int = 3

}