package com.johnny.swapub.home.item

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.johnny.swapub.NavigationDirections
import com.johnny.swapub.R
import com.johnny.swapub.databinding.FragmentHomeItemBinding
import com.johnny.swapub.ext.getVmFactory
import com.johnny.swapub.home.HomeTypeFilter
import com.johnny.swapub.util.Logger
import kotlinx.android.synthetic.main.fragment_home.*

class HomeItemFragment(val homeTypeFilter: HomeTypeFilter) : Fragment() {

    val viewModel by viewModels<HomeItemViewModel> { getVmFactory(homeTypeFilter) }

    private lateinit var  binding: FragmentHomeItemBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment




        val binding = FragmentHomeItemBinding.inflate(inflater, container, false)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this


        val adapter = HomeItemAdapter(HomeItemAdapter.OnClickListener {
            viewModel.displayItemProductDetails(it)
        })
        binding.recyclerHomeItem.adapter = adapter
        viewModel.itemInfo.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(homeTypeFilter.value == "newest"){
                    adapter.submitList(it)
                }
            }
        })
        viewModel.userI.observe(viewLifecycleOwner, Observer {
             Logger.d("userI$it")
        })

        viewModel.itemPlaceInfo.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (homeTypeFilter.value == "nearest") {
                    adapter.submitList(it)
                    Logger.d("placept$it")
                }
            }
        })

        viewModel.navigateToSelecteditemInfo.observe(this.viewLifecycleOwner, Observer {
            it?.let {
                // Must find the NavController from the Fragment
                findNavController().navigate(NavigationDirections.actionGlobalProductFragment(it))
                // Tell the ViewModel we've made the navigate call to prevent multiple navigation
                viewModel.displayItemProductDetailsComplete()
            }
        })

        val swipeRefresh = binding.layoutSwipeRefreshHomeItem
        swipeRefresh.setOnRefreshListener {
            viewModel.getProductsResult()
            viewModel.getProductsWithPlace()
            swipeRefresh.isRefreshing = false
        }
        Handler().postDelayed({
            viewModel.getProductsWithPlace()
        },1000)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        Logger.d("gggggg$homeTypeFilter")

    }


}