package com.johnny.swapub.wishNews

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.johnny.swapub.MainViewModel
import com.johnny.swapub.R
import com.johnny.swapub.databinding.ProductFragmentBinding
import com.johnny.swapub.databinding.WishNewsFragmentBinding
import com.johnny.swapub.product.ProductFragment
import com.johnny.swapub.product.ProductViewModel
import ext.getVmFactory

class WishNewsFragment : Fragment() {

    companion object {
        fun newInstance() = WishNewsFragment()
    }

    val viewModel by viewModels<WishNewsViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = WishNewsFragmentBinding.inflate(inflater, container,
            false)




        return binding.root

    }



}