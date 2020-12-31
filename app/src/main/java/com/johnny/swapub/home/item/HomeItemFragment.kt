package com.johnny.swapub.home.item

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.johnny.swapub.NavigationDirections
import com.johnny.swapub.R
import com.johnny.swapub.databinding.FragmentHomeItemBinding
import com.johnny.swapub.ext.getVmFactory
import com.johnny.swapub.home.HomeTypeFilter


class HomeItemFragment(val homeTypeFilter: HomeTypeFilter) : Fragment() {

    val viewModel by viewModels<HomeItemViewModel> { getVmFactory(homeTypeFilter) }

    private lateinit var binding: FragmentHomeItemBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        val binding = FragmentHomeItemBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this

        val adapter = HomeItemAdapter(HomeItemAdapter.OnClickListener {
            viewModel.displayItemProductDetails(it)
        })

        binding.recyclerHomeItem.adapter = adapter

        viewModel.itemInfo.observe(viewLifecycleOwner, Observer {
            if (homeTypeFilter.value == "newest") {
                viewModel.itemInfoSelector.value = it
            }
        })

        viewModel.userI.observe(viewLifecycleOwner, Observer {
        })

        viewModel.itemPlaceInfo.observe(viewLifecycleOwner, Observer {
            if (homeTypeFilter.value == "nearest") {
                viewModel.itemInfoSelector.value = it
            }
        })

        //distinguish which viewpager and submitList what product
        viewModel.itemInfoSelector.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) {
                binding.remindText.setText(R.string.nearest_page)
            } else {
                binding.remindText.visibility = View.GONE
                adapter.submitList(it)
            }
        })

        viewModel.navigateToSelecteditemInfo.observe(this.viewLifecycleOwner, Observer {
            it?.let {
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
        }, 1000)

        return binding.root
    }
}