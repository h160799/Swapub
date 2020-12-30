package com.johnny.swapub.club.clubFashion

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.johnny.swapub.data.Product
import com.johnny.swapub.databinding.ItemClubElectronicProductBinding
import com.johnny.swapub.databinding.ItemClubFashionBinding
import com.johnny.swapub.util.Logger


class ClubFashionAdapter (val onClickListener: OnClickListener) :
    ListAdapter<Product, ClubFashionAdapter.ClubFashionViewHolder>(ClubFashionViewHolder) {

    class ClubFashionViewHolder(private var binding: ItemClubFashionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.propertyFashion = product
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }

        companion object DiffCallback : DiffUtil.ItemCallback<Product>() {

            fun from(parent: ViewGroup): ClubFashionViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemClubFashionBinding.inflate(layoutInflater, parent, false)

                return ClubFashionViewHolder(binding)
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
    ): ClubFashionViewHolder {
        return ClubFashionViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ClubFashionViewHolder, position: Int) {
        val product = getItem(position)
        holder.itemView.setOnClickListener {
            Logger.d("product$product")
            onClickListener.onClick(product)
        }
        holder.bind(product)
    }

    class OnClickListener(val clickListener: (product: Product) -> Unit) {
        fun onClick(product: Product) = clickListener(product)
    }
}
