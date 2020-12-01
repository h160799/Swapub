package com.johnny.swapub.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.johnny.swapub.R
import com.johnny.swapub.databinding.SearchFragmentBinding
import com.johnny.swapub.ext.getVmFactory

class SearchFragment : Fragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    val viewModel by viewModels<SearchViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = SearchFragmentBinding.inflate(inflater, container,
            false)

        binding.goBack.setOnClickListener {
            findNavController().navigate(R.id.action_global_homeFragment)
        }


        return binding.root
    }



}