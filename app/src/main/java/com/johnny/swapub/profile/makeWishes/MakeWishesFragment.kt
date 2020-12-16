package com.johnny.swapub.profile.makeWishes

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.johnny.swapub.R
import com.johnny.swapub.databinding.MakeWishesFragmentBinding
import com.johnny.swapub.databinding.TradingPostFragmentBinding
import com.johnny.swapub.ext.getVmFactory
import com.johnny.swapub.myTrading.tradingPost.TradingPostViewModel
import com.johnny.swapub.util.Logger


class MakeWishesFragment : Fragment() {

        val viewModel by viewModels<MakeWishesViewModel> { getVmFactory() }

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val binding = MakeWishesFragmentBinding.inflate(inflater, container,
                false)
            binding.viewModel = viewModel
            binding.lifecycleOwner = this



            binding.editTextWishable.setOnCheckedChangeListener { _, check ->
                viewModel.wishableSelect.value = check
            }


            binding.postContent.setOnClickListener {
                viewModel.postTradingInfo(viewModel.addProduct())
                findNavController().navigate(R.id.action_global_profileFragment)
            }

            binding.goBack.setOnClickListener {
                findNavController().navigate(R.id.action_global_profileFragment)
            }

            return binding.root
        }



    }