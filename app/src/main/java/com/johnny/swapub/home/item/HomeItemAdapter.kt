package com.johnny.swapub.home.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.johnny.swapub.data.Product
import com.johnny.swapub.databinding.ItemHomeGridBinding
import com.johnny.swapub.databinding.ItemHomeGridBinding.inflate

class HomeItemAdapter(val onClickListener: OnClickListener) :
        ListAdapter<Product, HomeItemAdapter.HomeItemViewHolder>(HomeItemViewHolder) {

    class HomeItemViewHolder(private var binding: ItemHomeGridBinding) :
            RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.homeProperty = product
            binding.executePendingBindings()
        }

        companion object DiffCallback : DiffUtil.ItemCallback<Product>() {

            fun from(parent: ViewGroup): HomeItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = inflate(layoutInflater, parent, false)

                return HomeItemViewHolder(binding)
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
                return oldItem.user == newItem.user
            }
        }
    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int,
    ): HomeItemViewHolder {
        return HomeItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: HomeItemViewHolder, position: Int) {
        val product = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(product)
        }
        holder.bind(product)
    }

    class OnClickListener(val clickListener: (product: Product) -> Unit) {
        fun onClick(product: Product) = clickListener(product)
    }
}
