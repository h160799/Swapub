package com.johnny.swapub.messageHistory

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.johnny.swapub.R
import com.johnny.swapub.databinding.FragmentMessageHistoryBinding
import com.johnny.swapub.wishNews.WishNewsViewModel
import ext.getVmFactory

class MessageHistoryFragment : Fragment() {

    companion object {
        fun newInstance() = MessageHistoryFragment()
    }

    val viewModel by viewModels<MessageHistoryViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMessageHistoryBinding.inflate(inflater, container, false)

//        val adapter = MessageHistoryAdapter()
//        binding.recyclerMessageHistoryItem.adapter = adapter
//
//        viewModel.allMessageHistory.observe(viewLifecycleOwner, Observer {
//            it?.let{
//                adapter.submitList(it)
//            }
//        })



        return binding.root
    }

}