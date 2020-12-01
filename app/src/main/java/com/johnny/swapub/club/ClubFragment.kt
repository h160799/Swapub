package com.johnny.swapub.club

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.johnny.swapub.R
import com.johnny.swapub.databinding.ClubFragmentBinding
import com.johnny.swapub.ext.getVmFactory

class ClubFragment : Fragment() {

    companion object {
        fun newInstance() =ClubFragment()
    }

    val viewModel by viewModels<ClubViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = ClubFragmentBinding.inflate(inflater, container,
            false)

        binding.goBack.setOnClickListener {
            findNavController().navigate(R.id.action_global_homeFragment)
        }
        return binding.root

    }

}