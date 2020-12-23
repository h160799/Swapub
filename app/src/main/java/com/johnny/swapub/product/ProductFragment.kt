package com.johnny.swapub.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.johnny.swapub.R
import com.johnny.swapub.databinding.ProductFragmentBinding
import com.johnny.swapub.ext.getVmFactory
import com.johnny.swapub.util.Logger

class ProductFragment : Fragment() {

    val viewModel by viewModels<ProductViewModel> {
        getVmFactory(
            ProductFragmentArgs.fromBundle(
                requireArguments()
            ).productArg
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = ProductFragmentBinding.inflate(
            inflater, container,
            false
        )

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val adapter = ProductGalleryAdapter()

        binding.recyclerDetailGallery.adapter = adapter
        viewModel.productDetail.observe(viewLifecycleOwner, Observer {
            it?.let {
                val imageList = mutableListOf<String>()
                for (image in it.productImage!!) {
                    imageList.add(image)
                }
                adapter.submitList(imageList)
            }
        })

        viewModel.userFavorList.observe(viewLifecycleOwner, {
            Logger.d("userFavorList$it")
            viewModel.isFavor(it)
        })

        binding.addFavorite.setOnClickListener {
            if (viewModel.isFavor.value == true) {
                viewModel.isFavor.value = false
                viewModel.productDetail.value?.id?.let { productId ->
                    viewModel.removeProductToFavorList(
                        productId
                    )
                }
            } else {
                viewModel.isFavor.value = true
                viewModel.productDetail.value?.id?.let { productId ->
                    viewModel.addProductToFavorList(
                        productId
                    )
                }
            }
        }

        viewModel.isFavor.observe(viewLifecycleOwner, {
            Logger.d("isFavor$it")
        })

        binding.interestToTrade.setOnClickListener {

            viewModel.getSenderInfo(viewModel.userId)

            val chatRoom = viewModel.addChatRoom()
            viewModel.senderInfo.value?.let { it1 -> viewModel.postInterestMessage(chatRoom, it1) }
            Logger.d("")
        }

        viewModel.interestMessage.observe(viewLifecycleOwner, Observer {
            viewModel.getAddedChatRoom(viewModel.addChatRoom())
        })

        viewModel.addChatRoom.observe(viewLifecycleOwner, Observer {
            val message = viewModel.addMessage()
            it?.let {
                viewModel.postInterestMessageText(message, it)
            }
        })

        viewModel.interestMessageText.observe(viewLifecycleOwner, Observer {
            findNavController().navigate(ProductFragmentDirections.actionGlobalMessageHistoryFragment())
            Toast.makeText(context, R.string.send_successful, Toast.LENGTH_SHORT).show()
        })

        binding.goBack.setOnClickListener {
            findNavController().navigate(R.id.action_global_homeFragment)
        }
        return binding.root
    }
}



