package com.johnny.swapub.messageHistory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.johnny.swapub.databinding.FragmentMessageHistoryBinding
import com.johnny.swapub.ext.getVmFactory
import com.johnny.swapub.util.Logger
import kotlinx.android.synthetic.main.activity_main.*

class MessageHistoryFragment : Fragment() {

    val viewModel by viewModels<MessageHistoryViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMessageHistoryBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val adapter = MessageHistoryAdapter(MessageHistoryAdapter.OnClickListener {
            viewModel.navigateToConversation(it)
        }, viewModel)

        viewModel.liveChatRooms.observe(viewLifecycleOwner, {
            it?.let {
                Logger.d("ssssss$it")
                adapter.submitList(it)
            }
        })


        adapter.setHasStableIds(true)
        binding.recyclerMessageHistoryItem.adapter = adapter

        viewModel.navigateToConversation.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(
                    MessageHistoryFragmentDirections.actionGlobalConversationFragment(it)
                )
            }
        })

        (activity as AppCompatActivity).bottomNavView.visibility = View.VISIBLE
        return binding.root
    }
}

