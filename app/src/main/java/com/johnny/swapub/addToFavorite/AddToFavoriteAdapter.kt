package com.johnny.swapub.addToFavorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.johnny.swapub.data.Product
import com.johnny.swapub.databinding.ItemAddToFavoriteBinding
import com.johnny.swapub.databinding.ItemHomeGridBinding

class AddToFavoriteAdapter(val onClickListener: OnClickListener) :
    ListAdapter<Product, AddToFavoriteAdapter.AddToFavoriteViewHolder>(AddToFavoriteViewHolder) {

    class AddToFavoriteViewHolder(private var binding: ItemAddToFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.favoriteProperty= product
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }

        companion object DiffCallback : DiffUtil.ItemCallback<Product>() {

            fun from(parent: ViewGroup): AddToFavoriteViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemAddToFavoriteBinding.inflate(layoutInflater, parent, false)

                return AddToFavoriteViewHolder(binding)
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
    ): AddToFavoriteViewHolder {
        return AddToFavoriteViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: AddToFavoriteViewHolder, position: Int) {
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
