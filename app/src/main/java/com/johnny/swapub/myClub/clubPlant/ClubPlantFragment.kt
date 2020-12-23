package com.johnny.swapub.myClub.clubPlant

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.johnny.swapub.R
import com.johnny.swapub.databinding.ClubPlantFragmentBinding
import com.johnny.swapub.ext.getVmFactory

class ClubPlantFragment : Fragment() {


    val viewModel by viewModels<ClubPlantViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = ClubPlantFragmentBinding.inflate(inflater, container,
            false)

        return binding.root

    }



}