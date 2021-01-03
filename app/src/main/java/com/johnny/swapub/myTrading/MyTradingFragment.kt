package com.johnny.swapub.myTrading

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.johnny.swapub.databinding.MyTradingFragmentBinding
import com.johnny.swapub.ext.getVmFactory


class MyTradingFragment : Fragment() {

    val viewModel by viewModels<MyTradingViewModel> { getVmFactory() }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = MyTradingFragmentBinding.inflate(inflater, container,
                false)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val adapter = MyTradingAdapter(MyTradingAdapter.OnClickListener {
        }, viewModel)

        binding.recyclerMyTradingItem.adapter = adapter
        viewModel.myTradingList.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        viewModel.postProduct.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            adapter.submitList(it)
        })

        binding.editProduct.setOnClickListener {
            viewModel.editProduct.value = true
            viewModel.finishEditProduct.value = false
            binding.editProduct.visibility = View.GONE
            binding.finishProduct.visibility = View.VISIBLE
            adapter.notifyDataSetChanged()
        }

        binding.finishProduct.setOnClickListener {
            viewModel.finishEditProduct.value = true
            viewModel.editProduct.value = false
            binding.editProduct.visibility = View.VISIBLE
            binding.finishProduct.visibility = View.GONE
            adapter.notifyDataSetChanged()
        }

        binding.goBack.setOnClickListener {
            findNavController().navigateUp()
        }
        return binding.root
    }
}