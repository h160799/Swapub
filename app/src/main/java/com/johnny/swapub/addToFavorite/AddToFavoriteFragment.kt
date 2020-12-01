package com.johnny.swapub.addToFavorite

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.johnny.swapub.R
import com.johnny.swapub.databinding.AddToFavoriteFragmentBinding
import com.johnny.swapub.databinding.MyFavoriteFragmentBinding
import com.johnny.swapub.ext.getVmFactory
import com.johnny.swapub.myFavorite.MyFavoriteFragment
import com.johnny.swapub.myFavorite.MyFavoriteViewModel

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

        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }

}