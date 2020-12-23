package com.johnny.swapub.privacyPolicy

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.johnny.swapub.R
import com.johnny.swapub.databinding.PrivacyPolicyFragmentBinding
import com.johnny.swapub.databinding.SettingFragmentBinding
import com.johnny.swapub.ext.getVmFactory
import com.johnny.swapub.setting.SettingViewModel

class PrivacyPolicyFragment : Fragment() {

    val viewModel by viewModels<PrivacyPolicyViewModel> { getVmFactory() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = PrivacyPolicyFragmentBinding.inflate(inflater, container,
            false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel


        binding.goBack.setOnClickListener {
            findNavController().navigate(R.id.action_global_homeFragment)
        }

    return binding.root
    }



}