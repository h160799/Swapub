package com.johnny.swapub.myClub.clubBookShare

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.johnny.swapub.R
import com.johnny.swapub.databinding.ClubArtWorkFragmentBinding
import com.johnny.swapub.ext.getVmFactory

class ClubBookShareFragment : Fragment() {

    val viewModel by viewModels<ClubBookShareViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = ClubArtWorkFragmentBinding.inflate(inflater, container,
            false)

        return binding.root
    }



}