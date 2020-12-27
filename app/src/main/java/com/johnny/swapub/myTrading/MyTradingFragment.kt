package com.johnny.swapub.myTrading

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.johnny.swapub.R
import com.johnny.swapub.databinding.MyTradingFragmentBinding
import com.johnny.swapub.ext.getVmFactory
import com.johnny.swapub.myFavorite.MyFavoriteAdapter
import com.johnny.swapub.util.Logger
import java.util.Observer

class MyTradingFragment : Fragment() {



    val viewModel by viewModels<MyTradingViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = MyTradingFragmentBinding.inflate(inflater, container,
            false)


        val adapter = MyTradingAdapter(MyTradingAdapter.OnClickListener {
        })
        binding.recyclerMyTradingItem.adapter = adapter
        viewModel.myTradingList.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let {
                adapter.submitList(it)
                Logger.d( "uuu$it")
            }
        })


        viewModel.postProduct.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            adapter.submitList(it)
            Logger.w("getPostProduct$it")
        })






        binding.goBack.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }



}