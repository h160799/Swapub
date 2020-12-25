package com.johnny.swapub.myClub

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.johnny.swapub.R
import com.johnny.swapub.databinding.MyClubFragmentBinding
import com.johnny.swapub.ext.getVmFactory

class MyClubFragment : Fragment() {

    val viewModel by viewModels<MyClubViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = MyClubFragmentBinding.inflate(
            inflater, container,
            false
        )
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.userF.observe(viewLifecycleOwner, Observer {
            it.clubList?.let { it1 -> viewModel.getClub(it1) }
            if (it.clubList?.contains("clubArtWork")!!) {
                viewModel.getClubArtWork.value = true
            }
            if (it.clubList?.contains("clubBookShare")!!) {
                viewModel.getClubBookShare.value = true
            }
            if (it.clubList?.contains("clubElectronicProduct")!!) {
                viewModel.getClubElectronicProduct.value = true
            }
            if (it.clubList?.contains("clubFashion")!!) {
                viewModel.getClubFashion.value = true
            }
            if (it.clubList?.contains("clubLiveLife")!!) {
                viewModel.getClubLiveLife.value = true
            }
            if (it.clubList?.contains("clubMakeup")!!) {
                viewModel.getClubMakeup.value = true
            }
            if (it.clubList?.contains("clubMenClothes")!!) {
                viewModel.getClubMenClothes.value = true
            }
            if (it.clubList?.contains("clubPlant")!!) {
                viewModel.getClubPlant.value = true
            }
            if (it.clubList?.contains("clubSporty")!!) {
                viewModel.getClubSporty.value = true
            }
            if (it.clubList?.contains("clubVideoGame")!!) {
                viewModel.getClubVideoGame.value = true
            }
            if (it.clubList?.contains("clubVolunteer")!!) {
                viewModel.getClubVolunteer.value = true
            }
            if (it.clubList?.contains("clubWomenMenClothes")!!) {
                viewModel.getClubWomenMenClothes.value = true
            }
        })

        binding.goBack.setOnClickListener {
            findNavController().navigate(R.id.action_global_profileFragment)
        }

        binding.myArtwork.setOnClickListener {
            findNavController().navigate(R.id.action_global_clubArtWorkFragment)
        }
        binding.myBookShare.setOnClickListener {
            findNavController().navigate(R.id.action_global_clubBookShareFragment)
        }
        binding.myConsumerElectronic.setOnClickListener {
            findNavController().navigate(R.id.action_global_clubElectronicProductFragment)
        }
        binding.myFashion.setOnClickListener {
            findNavController().navigate(R.id.action_global_clubFashionFragment)
        }
        binding.myLifeLife.setOnClickListener {
            findNavController().navigate(R.id.action_global_clubLiveLifeFragment)
        }
        binding.myMakeup.setOnClickListener {
            findNavController().navigate(R.id.action_global_clubMakeupFragment)
        }
        binding.myMenClothes.setOnClickListener {
            findNavController().navigate(R.id.action_global_clubMenClothesFragment)
        }
        binding.myPlantDesign.setOnClickListener {
            findNavController().navigate(R.id.action_global_clubPlantFragment)
        }
        binding.mySporty.setOnClickListener {
            findNavController().navigate(R.id.action_global_clubSportyFragment)
        }
        binding.myVideoGames.setOnClickListener {
            findNavController().navigate(R.id.action_global_clubVideoGameFragment)
        }
        binding.myVolunteer.setOnClickListener {
            findNavController().navigate(R.id.action_global_clubVolunteerFragment)
        }
        binding.myWomenClothes.setOnClickListener {
            findNavController().navigate(R.id.action_global_clubWomenClothesFragment)
        }

        return binding.root
    }


}