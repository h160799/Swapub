package com.johnny.swapub.club.clubFashion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.johnny.swapub.R
import com.johnny.swapub.databinding.ClubFashionFragmentBinding
import com.johnny.swapub.ext.getVmFactory
import com.johnny.swapub.util.Logger

class ClubFashionFragment : Fragment() {



    val viewModel by viewModels<ClubFashionViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = ClubFashionFragmentBinding.inflate(inflater, container,
            false)

        viewModel.userClubList.observe(viewLifecycleOwner, {
            Logger.d("userClubList$it")
            viewModel.isClub(it)
        })
        binding.addClubFashion.setOnClickListener {
            if (viewModel.isClub.value == true) {
                viewModel.isClub.value = false
                viewModel.removeToClubList("clubFashion")
            } else {
                viewModel.isClub.value = true
                viewModel.addToClubList("clubFashion")
            }
        }
        viewModel.isClub.observe(viewLifecycleOwner, {
            Logger.d("isClub$it")
        })

        binding.goBack.setOnClickListener {
            findNavController().navigateUp()
        }
        return binding.root


    }



}