package com.johnny.swapub.myTrading.tradingPost

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.johnny.swapub.R
import com.johnny.swapub.databinding.MyTradingFragmentBinding
import com.johnny.swapub.databinding.TradingPostFragmentBinding
import com.johnny.swapub.ext.getVmFactory
import com.johnny.swapub.myTrading.MyTradingViewModel
import com.johnny.swapub.util.Logger

class TradingPostFragment : Fragment() {

    val viewModel by viewModels<TradingPostViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = TradingPostFragmentBinding.inflate(inflater, container,
            false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        binding.tradingPostOk.setOnClickListener {
            viewModel.postTradingInfo(viewModel.addProduct())
            findNavController().navigate(R.id.action_global_myTradingFragment)

        }

//        viewModel.tradingPostEditText.observe(viewLifecycleOwner, Observer {
//            Logger.d("520$it")
//        })

        binding.goBack.setOnClickListener {
            findNavController().navigate(R.id.action_global_myTradingFragment)
        }

        return binding.root
    }



}