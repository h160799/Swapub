package com.johnny.swapub.messageHistory.TradingStyle

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.johnny.swapub.databinding.TradingStyleFragmentBinding
import com.johnny.swapub.ext.getVmFactory

class TradingStyleFragment : Fragment() {

    companion object {
        fun newInstance() = TradingStyleFragment()
    }

    val viewModel by viewModels<TradingStyleViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = TradingStyleFragmentBinding.inflate(inflater, container,
            false)


        return binding.root

    }

}