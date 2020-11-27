package com.johnny.swapub.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.johnny.swapub.home.item.HomeItemFragment

class HomeAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return HomeItemFragment(HomeTypeFilter.values()[position])
    }

    override fun getCount() = HomeTypeFilter.values().size

    override fun getPageTitle(position: Int): CharSequence? {
        return HomeTypeFilter.values()[position].value
    }
}