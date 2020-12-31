package com.johnny.swapub.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.johnny.swapub.R
import com.johnny.swapub.databinding.ProfileFragmentBinding
import com.johnny.swapub.ext.getVmFactory
import com.johnny.swapub.util.Logger
import com.johnny.swapub.util.UserManager
import com.johnny.swapub.util.UserManager.userId

class ProfileFragment : Fragment() {

    val viewModel by viewModels<ProfileViewModel> { getVmFactory()  }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = ProfileFragmentBinding.inflate(inflater, container,
            false)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val adapter = ProfileAdapter(ProfileAdapter.OnClickListener {
        })

        binding.recyclerMyWishItem.adapter = adapter
        viewModel.getWishProduct.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let {
                adapter.submitList(it)
                Logger.d( "5566$it")
            }
        })

        binding.makeAWish.setOnClickListener {
            findNavController().navigate(R.id.action_global_makeWishesFragment)
        }
        binding.myTrading.setOnClickListener {
            findNavController().navigate(R.id.action_global_myTradingFragment)
        }
        binding.myClub.setOnClickListener {
            findNavController().navigate(R.id.action_global_myClubFragment)
        }
        binding.myFavorite.setOnClickListener {
            findNavController().navigate(R.id.action_global_myFavoriteFragment)
        }

        val swipeRefresh = binding.layoutSwipeRefreshProfile
        swipeRefresh.setOnRefreshListener {
            viewModel.getWishContent(userId)
            swipeRefresh.isRefreshing = false
        }

        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModel by viewModels<ProfileViewModel> { getVmFactory() }

    }



}