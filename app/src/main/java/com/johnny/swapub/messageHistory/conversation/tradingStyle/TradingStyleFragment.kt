package com.johnny.swapub.messageHistory.conversation.tradingStyle

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.johnny.swapub.R
import com.johnny.swapub.databinding.TradingStyleFragmentBinding
import com.johnny.swapub.ext.getVmFactory
import com.johnny.swapub.util.Logger

class TradingStyleFragment : Fragment() {


    val viewModel by viewModels<TradingStyleViewModel> { getVmFactory(TradingStyleFragmentArgs.fromBundle(requireArguments()).chatRoom) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = TradingStyleFragmentBinding.inflate(inflater, container,
            false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        binding.radiosChooseTradingStyle.setOnCheckedChangeListener { _, check ->
            when (check) {
                R.id.radio_trading_by_goods -> {
                    viewModel.tradingTypeSelect.value = getString(R.string.trading_product)
                }
                R.id.radio_trading_by_money -> {
                    viewModel.tradingTypeSelect.value = getString(R.string.trading_money)
                }
                R.id.radio_trading_by_service -> {
                    viewModel.tradingTypeSelect.value = getString(R.string.trading_service)
                }
                else -> {
                    viewModel.tradingTypeSelect.value = getString(R.string.trading_free)
                }
            }
        }


        viewModel.tradingEditText.observe(viewLifecycleOwner, Observer {
         Logger.w("nnn${it}")
     })


        binding.chooseTradingStyleSelecting.setOnClickListener {
            viewModel.chatRoom.id?.let { it1 -> viewModel.postTradingType(it1,viewModel.addTradingType()) }
            viewModel.chatRoom.id?.let { chatRoom -> viewModel.updateTradingSelect(chatRoom, true) }
            Toast.makeText(context, R.string.trading_select, Toast.LENGTH_SHORT).show()
            viewModel.chatRoom.tradingSelect = true
            findNavController().navigate(TradingStyleFragmentDirections.actionGlobalConversationFragment(viewModel.chatRoom))


            Logger.d("7777${viewModel.chatRoom.tradingSelect}")

        }



        binding.goBack.setOnClickListener {
                findNavController().navigate(TradingStyleFragmentDirections.actionGlobalConversationFragment(viewModel.chatRoom))
            }




        return binding.root

    }

}