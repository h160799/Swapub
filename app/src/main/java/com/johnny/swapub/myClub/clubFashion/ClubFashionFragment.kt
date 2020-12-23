package com.johnny.swapub.myClub.clubFashion

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.johnny.swapub.R
import com.johnny.swapub.databinding.ClubArtWorkFragmentBinding
import com.johnny.swapub.databinding.ClubFashionFragmentBinding
import com.johnny.swapub.ext.getVmFactory
import com.johnny.swapub.myClub.clubArtWork.ClubArtWorkViewModel

class ClubFashionFragment : Fragment() {



    val viewModel by viewModels<ClubFashionViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = ClubFashionFragmentBinding.inflate(inflater, container,
            false)

        return binding.root


    }



}