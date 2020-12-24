package com.johnny.swapub.club.clubBookShare

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.johnny.swapub.R
import com.johnny.swapub.databinding.ClubArtWorkFragmentBinding
import com.johnny.swapub.databinding.ClubBookShareFragmentBinding
import com.johnny.swapub.ext.getVmFactory
import com.johnny.swapub.util.Logger

class ClubBookShareFragment : Fragment() {

    val viewModel by viewModels<ClubBookShareViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = ClubBookShareFragmentBinding.inflate(inflater, container,
            false)

        viewModel.userClubList.observe(viewLifecycleOwner, {
            Logger.d("userClubList$it")
            viewModel.isClub(it)
        })
        binding.addClubBookShare.setOnClickListener {
            if (viewModel.isClub.value == true) {
                viewModel.isClub.value = false
                viewModel.removeToClubList("clubBookShare")
            } else {
                viewModel.isClub.value = true
                viewModel.addToClubList("clubBookShare")
            }
        }
        viewModel.isClub.observe(viewLifecycleOwner, {
            Logger.d("isClub$it")
        })

        binding.goBack.setOnClickListener {
            findNavController().navigate(R.id.action_global_clubFragment)
        }

        return binding.root
    }



}