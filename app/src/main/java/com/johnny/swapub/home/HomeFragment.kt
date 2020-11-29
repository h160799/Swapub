package com.johnny.swapub.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.johnny.swapub.R
import com.johnny.swapub.databinding.FragmentHomeBinding
import com.johnny.swapub.databinding.FragmentMessageHistoryBinding


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeBinding.inflate(inflater, container,
            false)


        return binding.root
    }
}