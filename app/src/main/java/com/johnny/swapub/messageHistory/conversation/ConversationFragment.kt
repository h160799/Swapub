package com.johnny.swapub.messageHistory.conversation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.johnny.swapub.databinding.ConversationFragmentBinding
import com.johnny.swapub.ext.getVmFactory

class ConversationFragment : Fragment() {

    companion object {
        fun newInstance() = ConversationFragment()
    }

    val viewModel by viewModels<ConversationViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = ConversationFragmentBinding.inflate(inflater, container,
            false)


        return binding.root

    }
}