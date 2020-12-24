package com.johnny.swapub.myClub

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.johnny.swapub.R
import com.johnny.swapub.databinding.MyClubFragmentBinding
import com.johnny.swapub.ext.getVmFactory
import com.johnny.swapub.util.Logger

class MyClubFragment : Fragment() {

    companion object {
        fun newInstance() = MyClubFragment()
    }

    val viewModel by viewModels<MyClubViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = MyClubFragmentBinding.inflate(inflater, container,
            false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel


        viewModel.userF.observe(viewLifecycleOwner, Observer {
            it.clubList?.let { it1 -> viewModel.getClub(it1) }
            if (it.clubList?.contains("clubArtWork")!!) {
                viewModel.getClubArtWork.value = true
            }
            else if (it.clubList.contains("clubBookShare")) {
                viewModel.getClubBookShare.value = true
            }
            else if (it.clubList.contains("clubElectronicProduct")) {
                viewModel.getClubElectronicProduct.value = true
            }
            else if (it.clubList.contains("clubFashion")) {
                viewModel.getClubFashion.value = true
            }
            else if (it.clubList.contains("clubLiveLife")) {
                viewModel.getClubLiveLife.value = true
            }
            else if (it.clubList.contains("clubMakeup")) {
                viewModel.getClubMakeup.value = true
            }
            else if (it.clubList.contains("clubMenClothes")) {
                viewModel.getClubMenClothes.value = true
            }
            else if (it.clubList.contains("clubPlant")) {
                viewModel.getClubPlant.value = true
            }
            else if (it.clubList.contains("clubSporty")) {
                viewModel.getClubSporty.value = true
            }
            else if (it.clubList.contains("clubVideoGame")) {
                viewModel.getClubVideoGame.value = true
            }
            else if (it.clubList.contains("clubVolunteer")) {
                viewModel.getClubVolunteer.value = true
            }
            else if (it.clubList.contains("clubWomenMenClothes")) {
                viewModel.getClubWomenMenClothes.value = true
            }

            Logger.d("list77777${it.clubList}")
        })

        binding.goBack.setOnClickListener {
            findNavController().navigate(R.id.action_global_profileFragment)
        }

        return binding.root
    }


}