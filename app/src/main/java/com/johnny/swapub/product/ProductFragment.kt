package com.johnny.swapub.product

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.johnny.swapub.R
import com.johnny.swapub.databinding.ProductFragmentBinding
import com.johnny.swapub.databinding.ProfileFragmentBinding
import com.johnny.swapub.databinding.SearchFragmentBinding
import com.johnny.swapub.messageHistory.MessageHistoryViewModel
import ext.getVmFactory

class ProductFragment : Fragment() {

    companion object {
        fun newInstance() = ProductFragment()
    }

    val viewModel by viewModels<ProductViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = ProductFragmentBinding.inflate(inflater, container,
            false)


        return binding.root

    }



}