package com.johnny.swapub.myTrading.tradingPost

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.johnny.swapub.R
import com.johnny.swapub.databinding.TradingPostFragmentBinding
import com.johnny.swapub.ext.getVmFactory

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

        binding.goBack.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }
}