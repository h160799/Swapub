package com.johnny.swapub.club.clubMenClothes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.johnny.swapub.NavigationDirections
import com.johnny.swapub.R
import com.johnny.swapub.databinding.ClubMenClothesFragmentBinding
import com.johnny.swapub.ext.getVmFactory
import com.johnny.swapub.myFavorite.MyFavoriteAdapter
import com.johnny.swapub.util.Logger
import java.util.Observer

class ClubMenClothesFragment : Fragment() {

    val viewModel by viewModels<ClubMenClothesViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = ClubMenClothesFragmentBinding.inflate(inflater, container,
            false)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val adapter = ClubMenClothesAdapter(ClubMenClothesAdapter.OnClickListener {
            viewModel.navigateToDetail(it)
        }, viewModel)

        viewModel.userClubList.observe(viewLifecycleOwner, {
            Logger.d("userClubList$it")
            viewModel.isClub(it)
        })
        binding.addClubMenClothes.setOnClickListener {
            if (viewModel.isClub.value == true) {
                viewModel.isClub.value = false
                viewModel.removeToClubList("clubMenClothes")
            } else {
                viewModel.isClub.value = true
                viewModel.addToClubList("clubMenClothes")
            }
        }
        viewModel.isClub.observe(viewLifecycleOwner, {
            Logger.d("isClub$it")
        })

        binding.recyclerMenClothes.adapter = adapter

        viewModel.menClothesProduct.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            adapter.submitList(it)
            Logger.w("ppp$it")
        })

        viewModel.navigateToDetail.observe(this.viewLifecycleOwner, androidx.lifecycle.Observer{
            it?.let {
                // Must find the NavController from the Fragment
                findNavController().navigate(NavigationDirections.actionGlobalProductFragment(it))
                // Tell the ViewModel we've made the navigate call to prevent multiple navigation
                viewModel.onDetailNavigated()
            }
        })




        binding.goBack.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root

    }



}