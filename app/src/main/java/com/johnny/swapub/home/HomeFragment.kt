package com.johnny.swapub.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.johnny.swapub.R
import com.johnny.swapub.databinding.FragmentHomeBinding
import com.johnny.swapub.databinding.FragmentMessageHistoryBinding
import kotlinx.android.synthetic.main.activity_main.*


class HomeFragment : Fragment() {
    private lateinit var homeViewPagerAdapter: HomeViewPagerAdapter
    private lateinit var viewPager: ViewPager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        homeViewPagerAdapter = HomeViewPagerAdapter(childFragmentManager)
        viewPager = view.findViewById(R.id.viewpager_home)
        viewPager.adapter = homeViewPagerAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeBinding.inflate(inflater, container,
            false)

        binding.buttonFloatingFavorite.setOnClickListener {
            findNavController().navigate(R.id.action_global_addToFavoriteFragment)
        }

        return binding.root
    }
}