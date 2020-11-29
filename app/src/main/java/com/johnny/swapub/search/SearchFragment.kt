package com.johnny.swapub.search

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.johnny.swapub.R
import com.johnny.swapub.databinding.ProfileFragmentBinding
import com.johnny.swapub.databinding.SearchFragmentBinding
import com.johnny.swapub.profile.ProfileViewModel
import ext.getVmFactory

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



        return binding.root
    }



}