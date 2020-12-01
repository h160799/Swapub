package com.johnny.swapub.myClub

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.johnny.swapub.R
import com.johnny.swapub.databinding.MyClubFragmentBinding
import com.johnny.swapub.ext.getVmFactory

class MyClubFragment : Fragment() {

    companion object {
        fun newInstance() = MyClubFragment()
    }

    val viewModel by viewModels<MyClubViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = MyClubFragmentBinding.inflate(inflater, container,
            false)

        binding.goBack.setOnClickListener {
            findNavController().navigate(R.id.action_global_profileFragment)
        }

        return binding.root
    }



}