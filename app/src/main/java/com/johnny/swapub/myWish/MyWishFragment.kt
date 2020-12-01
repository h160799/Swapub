package com.johnny.swapub.myWish

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.johnny.swapub.R
import com.johnny.swapub.databinding.MyWishFragmentBinding
import com.johnny.swapub.ext.getVmFactory

class MyWishFragment : Fragment() {

    companion object {
        fun newInstance() = MyWishFragment()
    }

    val viewModel by viewModels<MyWishViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = MyWishFragmentBinding.inflate(inflater, container,
            false)

        binding.goBack.setOnClickListener {
            findNavController().navigate(R.id.action_global_profileFragment)
        }

        return binding.root
    }



}