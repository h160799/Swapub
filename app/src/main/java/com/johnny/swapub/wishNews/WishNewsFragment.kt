package com.johnny.swapub.wishNews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.johnny.swapub.databinding.WishNewsFragmentBinding
import com.johnny.swapub.ext.getVmFactory
import com.johnny.swapub.util.Logger

class WishNewsFragment : Fragment() {

    val viewModel by viewModels<WishNewsViewModel> { getVmFactory() }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = WishNewsFragmentBinding.inflate(inflater, container,
                false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val adapter = WishNewsAdapter(WishNewsAdapter.OnClickListener {
        })

        binding.recyclerWishNewsItem.adapter = adapter
        viewModel.getAllWishProduct.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        val swipeRefresh = binding.layoutSwipeRefreshWishNewsItem
        swipeRefresh.setOnRefreshListener {
            viewModel.getAllWishContent()
            swipeRefresh.isRefreshing = false
        }
        return binding.root
    }
}