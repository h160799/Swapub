package com.johnny.swapub.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.johnny.swapub.databinding.ItemProductGalleryBinding

class ProductGalleryAdapter() :
        ListAdapter<String, ProductGalleryAdapter.ProductGalleryViewHolder>(ProductGalleryViewHolder) {

    class ProductGalleryViewHolder(private var binding: ItemProductGalleryBinding) :
            RecyclerView.ViewHolder(binding.root) {
        fun bind(string: String) {
            binding.imageUrl = string
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }

        companion object DiffCallback : DiffUtil.ItemCallback<String>() {

            fun from(parent: ViewGroup): ProductGalleryViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemProductGalleryBinding.inflate(layoutInflater, parent, false)

                return ProductGalleryViewHolder(binding)
            }

            override fun areItemsTheSame(
                    oldItem: String,
                    newItem: String,
            ): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(
                    oldItem: String,
                    newItem: String,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int,
    ): ProductGalleryViewHolder {
        return ProductGalleryViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ProductGalleryViewHolder, position: Int) {
        val string = getItem(position)
        holder.bind(string)
    }
}
