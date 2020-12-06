package com.johnny.swapub.addToFavorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.johnny.swapub.NavigationDirections
import com.johnny.swapub.databinding.AddToFavoriteFragmentBinding
import com.johnny.swapub.ext.getVmFactory
import com.johnny.swapub.util.Logger

class AddToFavoriteFragment : DialogFragment() {

    companion object {
        fun newInstance() = AddToFavoriteFragment()
    }

    val viewModel by viewModels<AddToFavoriteViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = AddToFavoriteFragmentBinding.inflate(inflater, container,
            false)

        binding.lifecycleOwner = this

        val adapter = AddToFavoriteAdapter(AddToFavoriteAdapter.OnClickListener {
            viewModel.displayItemProductDetails(it)
        })
        binding.recyclerProductImage.adapter = adapter
        viewModel.itemInformation.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
                Logger.d( "qqqqq$it")
            }
        })


        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }

}