package com.johnny.swapub.myClub.clubMakeup

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.johnny.swapub.R
import com.johnny.swapub.databinding.ClubArtWorkFragmentBinding
import com.johnny.swapub.databinding.ClubMakeupFragmentBinding
import com.johnny.swapub.ext.getVmFactory

class ClubMakeupFragment : Fragment() {


    val viewModel by viewModels<ClubMakeupViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = ClubMakeupFragmentBinding.inflate(inflater, container,
            false)

        return binding.root

    }



}