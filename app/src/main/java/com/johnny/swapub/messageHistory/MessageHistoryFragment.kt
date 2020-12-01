package com.johnny.swapub.messageHistory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.johnny.swapub.databinding.FragmentMessageHistoryBinding
import com.johnny.swapub.ext.getVmFactory
import com.johnny.swapub.util.Logger
import kotlinx.android.synthetic.main.fragment_message_history.view.*

class MessageHistoryFragment : Fragment() {

    val viewModel by viewModels<MessageHistoryViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMessageHistoryBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val adapter = MessageHistoryAdapter()

        binding.recyclerMessageHistoryItem.adapter = adapter
        viewModel.allMessageHistory.observe(viewLifecycleOwner, Observer {
            it?.let{
                adapter.submitList(it)
            }
        })

        viewModel.allMessageHistory.observe(viewLifecycleOwner, {
            it?.let {
                Logger.d("1234567$it")
            }
        })

        return binding.root
    }

}