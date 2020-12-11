package com.johnny.swapub.messageHistory.TradingStyle

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.johnny.swapub.R
import com.johnny.swapub.data.ChatRoom
import com.johnny.swapub.databinding.TradingStyleFragmentBinding
import com.johnny.swapub.ext.getVmFactory
import com.johnny.swapub.messageHistory.conversation.ConversationFragmentArgs

class TradingStyleFragment : Fragment() {

    companion object {
        fun newInstance() = TradingStyleFragment()
    }

    val viewModel by viewModels<TradingStyleViewModel> { getVmFactory(TradingStyleFragmentArgs.fromBundle(requireArguments()).chatRoom) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = TradingStyleFragmentBinding.inflate(inflater, container,
            false)


        binding.goBack.setOnClickListener {
                findNavController().navigate(TradingStyleFragmentDirections.actionGlobalConversationFragment(viewModel.chatRoom))

            }


        return binding.root

    }

}