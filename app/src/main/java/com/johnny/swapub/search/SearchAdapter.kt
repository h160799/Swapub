package com.johnny.swapub.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.johnny.swapub.data.Product
import com.johnny.swapub.databinding.ItemSearchGridBinding


class SearchAdapter(val onClickListener: OnClickListener, viewModel: SearchViewModel) :
        ListAdapter<Product, SearchAdapter.SearchViewHolder>(SearchViewHolder) {

    class SearchViewHolder(private var binding: ItemSearchGridBinding) :
            RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.mySearchProperty = product
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }

        companion object DiffCallback : DiffUtil.ItemCallback<Product>() {

            fun from(parent: ViewGroup): SearchViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemSearchGridBinding.inflate(layoutInflater, parent, false)

                return SearchViewHolder(binding)
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
    ): SearchViewHolder {
        return SearchViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
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