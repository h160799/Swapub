package com.johnny.swapub.messageHistory.conversation.tradingSuccessOrNot

import android.animation.Animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.johnny.swapub.DealDialogDirections
import com.johnny.swapub.R
import com.johnny.swapub.data.ChatRoom
import com.johnny.swapub.databinding.TradingSuccessorNotFragmentBinding
import com.johnny.swapub.ext.getVmFactory
import com.johnny.swapub.util.Logger

class TradingSuccessorNotFragment : Fragment() {

    val viewModel by viewModels<TradingSuccessorNotViewModel> {
        getVmFactory(
                TradingSuccessorNotFragmentArgs.fromBundle(requireArguments()).chatRoom)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = TradingSuccessorNotFragmentBinding.inflate(inflater, container,
                false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.tradingStyleOk.setOnClickListener {
            viewModel.chatRoom.productId?.let { it1 -> viewModel.updateProductTradable(it1, true) }
            binding.dealSuccessfulConstraint.visibility = View.VISIBLE

        }

        binding.animationDealSuccessful.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator?) {
            }

            override fun onAnimationEnd(p0: Animator?) {
                findNavController().navigate(TradingSuccessorNotFragmentDirections.actionGlobalConversationFragment(viewModel.chatRoom))
            }

            override fun onAnimationCancel(p0: Animator?) {
            }

            override fun onAnimationRepeat(p0: Animator?) {
            }
        })

        binding.animationDealSuccessful.playAnimation()

        binding.tradingStyleNo.setOnClickListener {
            viewModel.chatRoom.id?.let { it1 -> viewModel.deleteTradingType(it1) }
            Logger.d("666${viewModel.chatRoom.id}")
            viewModel.chatRoom.id?.let { it1 -> viewModel.updateTradingSelect(it1, false) }
            Toast.makeText(context, R.string.trading_status_no, Toast.LENGTH_SHORT).show()
            findNavController().navigate(TradingSuccessorNotFragmentDirections.actionGlobalConversationFragment(viewModel.chatRoom))
        }

        binding.goBack.setOnClickListener {
            findNavController().navigate(TradingSuccessorNotFragmentDirections.actionGlobalConversationFragment(viewModel.chatRoom))
        }

        return binding.root
    }
}
