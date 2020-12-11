package com.johnny.swapub.messageHistory.TradingStyle.tradingSuccessOrNot

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.johnny.swapub.R

class TradingSuccessorNotFragment : Fragment() {

    companion object {
        fun newInstance() = TradingSuccessorNotFragment()
    }

    private lateinit var viewModel: TradingSuccessorNotViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.trading_successor_not_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TradingSuccessorNotViewModel::class.java)
        // TODO: Use the ViewModel
    }

}