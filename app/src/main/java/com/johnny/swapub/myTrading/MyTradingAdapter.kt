package com.johnny.swapub.myTrading

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.johnny.swapub.data.Product
import com.johnny.swapub.databinding.ItemMyTradingBinding
import com.johnny.swapub.util.UserManager

class MyTradingAdapter(val onClickListener: OnClickListener, val viewModel: MyTradingViewModel) :
        ListAdapter<Product, MyTradingAdapter.MyTradingViewHolder>(MyTradingViewHolder) {

    class MyTradingViewHolder(private var binding: ItemMyTradingBinding) :
            RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product, viewModel: MyTradingViewModel) {
            binding.myTradingProperty = product

            if (viewModel.editProduct.value == true) {
                binding.removeProduct.visibility = View.VISIBLE
                binding.removeProduct.setOnClickListener {
                    product.id?.let { it1 -> viewModel.deleteProduct(it1) }
                    viewModel.getPostProduct(UserManager.userId)
                }
            } else {
                binding.removeProduct.visibility = View.GONE
            }
            if (viewModel.finishEditProduct.value == true) {
                binding.removeProduct.visibility = View.GONE
            } else {
                binding.removeProduct.visibility = View.VISIBLE
            }

            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }

        companion object DiffCallback : DiffUtil.ItemCallback<Product>() {
            fun from(parent: ViewGroup
            ): MyTradingViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemMyTradingBinding.inflate(layoutInflater, parent, false)

                return MyTradingViewHolder(binding)
            }

            override fun areItemsTheSame(
                    oldItem: Product,
                    newItem: Product,
            ): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(
                    oldItem: Product,
                    newItem: Product,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int,
    ): MyTradingViewHolder {
        return MyTradingViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyTradingViewHolder, position: Int) {
        val product = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(product)
        }
        holder.bind(product, viewModel)
    }

    class OnClickListener(val clickListener: (product: Product) -> Unit) {
        fun onClick(product: Product) = clickListener(product)
    }
}

