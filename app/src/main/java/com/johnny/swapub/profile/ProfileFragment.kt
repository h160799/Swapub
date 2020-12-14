package com.johnny.swapub.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.johnny.swapub.R
import com.johnny.swapub.databinding.ProfileFragmentBinding
import com.johnny.swapub.ext.getVmFactory

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = ProfileFragmentBinding.inflate(inflater, container,
            false)

        binding.myTrading.setOnClickListener {
            findNavController().navigate(R.id.action_global_myTradingFragment)
        }
        binding.myClub.setOnClickListener {
            findNavController().navigate(R.id.action_global_myClubFragment)
        }
        binding.myFavorite.setOnClickListener {
            findNavController().navigate(R.id.action_global_myFavoriteFragment)
        }


        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModel by viewModels<ProfileViewModel> { getVmFactory() }

    }

}