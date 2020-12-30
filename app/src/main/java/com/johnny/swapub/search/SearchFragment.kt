package com.johnny.swapub.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.johnny.swapub.NavigationDirections
import com.johnny.swapub.R
import com.johnny.swapub.data.Product
import com.johnny.swapub.databinding.SearchFragmentBinding
import com.johnny.swapub.ext.getVmFactory
import com.johnny.swapub.ext.hideKeyboard

class SearchFragment : Fragment() {

    val viewModel: SearchViewModel by viewModels<SearchViewModel> { getVmFactory() }
    lateinit var binding: SearchFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = SearchFragmentBinding.inflate(inflater, container,
            false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val adapter = SearchAdapter(SearchAdapter.OnClickListener{
            viewModel.navigateToDetail(it)
        }, viewModel)
        adapter.setHasStableIds(true)
        binding.recyclerviewSearch.adapter = adapter

        viewModel.navigateToDetail.observe(this.viewLifecycleOwner, Observer {
            it?.let {
                // Must find the NavController from the Fragment
                findNavController().navigate(NavigationDirections.actionGlobalProductFragment(it))
                // Tell the ViewModel we've made the navigate call to prevent multiple navigation
                viewModel.onDetailNavigated()
            }
        })



//        binding.editSearch.setOnEditorActionListener { v, actionId, event ->
//            if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
//                binding.editSearch.hideKeyboard()
//
//            }
//            return@setOnEditorActionListener false
//        }

        binding.editSearch.addTextChangedListener {
            viewModel.liveSearch.value = viewModel.allProducts.value?.let { it1 -> viewModel.editSearch.value?.let { it2 ->
                viewModel.filter(it1,
                    it2
                )
            } }
        }

        viewModel.liveSearch.observe(viewLifecycleOwner, Observer {
                it?.let {
                    adapter.submitList(it)
                }
            })


        binding.imageSearchClear.setOnClickListener {
            binding.editSearch.text.clear()
        }


        binding.goBack.setOnClickListener {
            findNavController().navigateUp()
        }
        return binding.root
    }



}


