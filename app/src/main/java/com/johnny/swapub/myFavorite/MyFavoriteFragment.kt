package com.johnny.swapub.myFavorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.johnny.swapub.R
import com.johnny.swapub.databinding.MyFavoriteFragmentBinding
import com.johnny.swapub.ext.getVmFactory
import com.johnny.swapub.util.Logger

class MyFavoriteFragment : Fragment() {



    val viewModel by viewModels<MyFavoriteViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = MyFavoriteFragmentBinding.inflate(inflater, container,
            false)

        val adapter = MyFavoriteAdapter(MyFavoriteAdapter.OnClickListener {
        })

        binding.recyclerMyFavoriteItem.adapter = adapter
        viewModel.favoriteListPage.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
                Logger.d( "$it")
            }
        })

        viewModel.userF.observe(viewLifecycleOwner, Observer {
            it.favoriteList?.let { it1 -> viewModel.getFavoriteProduct(it1) }
            Logger.d("list${it.favoriteList}")
        })



        viewModel.favoriteProduct.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            Logger.w("ppp$it")
        })

        binding.goBack.setOnClickListener {
            findNavController().navigate(R.id.action_global_profileFragment)
        }

        return binding.root
    }



}