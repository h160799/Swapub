package com.johnny.swapub.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.johnny.swapub.R
import com.johnny.swapub.SwapubApplication
import com.johnny.swapub.home.item.HomeItemFragment

@Suppress("DEPRECATION")
    class HomeViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

        override fun getCount(): Int = 2

        override fun getItem(i: Int): Fragment {
            return HomeItemFragment(HomeTypeFilter.values()[i])
        }

        override fun getPageTitle(position: Int): CharSequence {
            return when (position) {
                0 -> SwapubApplication.instance.getString(R.string.newest)
                else -> SwapubApplication.instance.getString(R.string.nearest)
            }
        }
    }
