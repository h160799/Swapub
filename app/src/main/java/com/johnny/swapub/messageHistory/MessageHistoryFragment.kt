package com.johnny.swapub.messageHistory

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.johnny.swapub.R

class MessageHistoryFragment : Fragment() {

    companion object {
        fun newInstance() = MessageHistoryFragment()
    }

    private lateinit var viewModel: MessageHistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.message_history_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MessageHistoryViewModel::class.java)
        // TODO: Use the ViewModel
    }

}