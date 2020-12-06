package com.johnny.swapub.myFavorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.johnny.swapub.data.Product
import com.johnny.swapub.data.User
import com.johnny.swapub.databinding.ItemMyFavoriteGridBinding

class MyFavoriteAdapter(val onClickListener: OnClickListener) :
    ListAdapter<Product, MyFavoriteAdapter.MyFavoriteViewHolder>(MyFavoriteViewHolder) {

    class MyFavoriteViewHolder(private var binding: ItemMyFavoriteGridBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.myFavoriteProperty = product
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }

        companion object DiffCallback : DiffUtil.ItemCallback<Product>() {

            fun from(parent: ViewGroup): MyFavoriteViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemMyFavoriteGridBinding.inflate(layoutInflater, parent, false)

                return MyFavoriteViewHolder(binding)
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
    ): MyFavoriteViewHolder {
        return MyFavoriteViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyFavoriteViewHolder, position: Int) {
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

