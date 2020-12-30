package com.johnny.swapub.club

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.johnny.swapub.R
import com.johnny.swapub.databinding.ClubFragmentBinding
import com.johnny.swapub.ext.getVmFactory
import java.util.*

class ClubFragment : Fragment() {

    companion object {
        fun newInstance() =ClubFragment()
    }

    val viewModel by viewModels<ClubViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = ClubFragmentBinding.inflate(inflater, container,
            false)

        binding.goBack.setOnClickListener {
            findNavController().navigate(R.id.action_global_homeFragment)
        }

        binding.artwork.setOnClickListener{
            findNavController().navigate(R.id.action_global_clubArtWorkFragment)
        }
        binding.bookShare.setOnClickListener{
            findNavController().navigate(R.id.action_global_clubBookShareFragment)
        }
        binding.consumerElectronic.setOnClickListener{
            findNavController().navigate(R.id.action_global_clubElectronicProductFragment)
        }
        binding.fashion.setOnClickListener{
            findNavController().navigate(R.id.action_global_clubFashionFragment)
        }
        binding.lifeLife.setOnClickListener{
            findNavController().navigate(R.id.action_global_clubLiveLifeFragment)
        }
        binding.makeup.setOnClickListener{
            findNavController().navigate(R.id.action_global_clubMakeupFragment)
        }
        binding.menClothes.setOnClickListener{
            findNavController().navigate(R.id.action_global_clubMenClothesFragment)
        }
        binding.plantDesign.setOnClickListener{
            findNavController().navigate(R.id.action_global_clubPlantFragment)
        }
        binding.sporty.setOnClickListener{
            findNavController().navigate(R.id.action_global_clubSportyFragment)
        }
        binding.videoGames.setOnClickListener{
            findNavController().navigate(R.id.action_global_clubVideoGameFragment)
        }
        binding.volunteer.setOnClickListener{
            findNavController().navigate(R.id.action_global_clubVolunteerFragment)
        }
        binding.womenClothes.setOnClickListener{
            findNavController().navigate(R.id.action_global_clubWomenClothesFragment)
        }




        return binding.root
    }

}