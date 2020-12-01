package com.johnny.swapub.home.item
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.johnny.swapub.NavigationDirections
import com.johnny.swapub.R
import com.johnny.swapub.databinding.FragmentHomeItemBinding
import com.johnny.swapub.ext.getVmFactory
import com.johnny.swapub.home.HomeTypeFilter
import com.johnny.swapub.messageHistory.MessageHistoryAdapter
import com.johnny.swapub.messageHistory.MessageHistoryViewModel
import com.johnny.swapub.util.Logger
import kotlin.reflect.typeOf

class HomeItemFragment(val homeTypeFilter: HomeTypeFilter) : Fragment() {

    val viewModel by viewModels<HomeItemViewModel> { getVmFactory() }


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
        ): View? {
            // Inflate the layout for this fragment

        val binding = FragmentHomeItemBinding.inflate(inflater, container, false)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val adapter = HomeItemAdapter(HomeItemAdapter.OnClickListener {
            viewModel.displayItemProductDetails(it)
        })
        binding.recyclerHomeItem.adapter = adapter
        viewModel.itemInfo.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
                Logger.d( "get$it")
            }
        })

        viewModel.navigateToSelecteditemInfo.observe(this.viewLifecycleOwner, Observer {
            it?.let {
                // Must find the NavController from the Fragment
                findNavController().navigate(NavigationDirections.actionGlobalHomeItemFragment(it))
                // Tell the ViewModel we've made the navigate call to prevent multiple navigation
                viewModel.displayItemProductDetailsComplete()
            }
        })


            return binding.root
        }

}