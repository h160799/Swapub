package com.johnny.swapub.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.johnny.swapub.R
import com.johnny.swapub.databinding.ProductFragmentBinding
import com.johnny.swapub.ext.getVmFactory
import com.johnny.swapub.util.Logger
import kotlinx.android.synthetic.main.item_club_grid.*

class ProductFragment : Fragment() {

    val viewModel by viewModels<ProductViewModel> { getVmFactory(ProductFragmentArgs.fromBundle(requireArguments()).productArg) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = ProductFragmentBinding.inflate(inflater, container,
            false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val adapter = ProductGalleryAdapter()

        binding.recyclerDetailGallery.adapter = adapter
        viewModel.productDetail.observe(viewLifecycleOwner, Observer {
            it?.let{
                val imageList = mutableListOf<String>()
                for (image in it.productImage!!){
                    imageList.add(image)
                }
                adapter.submitList(imageList)
            }
        })

        viewModel.productDetail.observe(viewLifecycleOwner, {
            it?.let {
                Logger.d("RRRRRR$it")
            }
        })



        binding.goBack.setOnClickListener {
            findNavController().navigate(R.id.action_global_homeFragment)
        }

        return binding.root

    }



}