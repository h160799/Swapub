package com.johnny.swapub.myFavorite

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.johnny.swapub.NavigationDirections
import com.johnny.swapub.databinding.MyFavoriteFragmentBinding
import com.johnny.swapub.ext.getVmFactory


class MyFavoriteFragment : Fragment() {

    val viewModel by viewModels<MyFavoriteViewModel> { getVmFactory() }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = MyFavoriteFragmentBinding.inflate(inflater, container,
                false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val adapter = MyFavoriteAdapter(MyFavoriteAdapter.OnClickListener {
            viewModel.navigateToDetail(it)
        }, viewModel)

        binding.recyclerMyFavoriteItem.adapter = adapter

        viewModel.favoriteListPage.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        viewModel.userF.observe(viewLifecycleOwner, Observer {
            it.favoriteList?.let { it1 -> viewModel.getFavoriteProduct(it1) }
        })

        viewModel.favoriteProduct.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        viewModel.navigateToDetail.observe(this.viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(NavigationDirections.actionGlobalProductFragment(it))
                viewModel.onDetailNavigated()
            }
        })

        binding.goBack.setOnClickListener {
            findNavController().navigateUp()
        }
        return binding.root
    }
}

