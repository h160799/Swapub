package com.johnny.swapub.club.clubElectronicProduct

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.johnny.swapub.data.Product
import com.johnny.swapub.databinding.ItemClubArtWorkBinding
import com.johnny.swapub.databinding.ItemClubElectronicProductBinding
import com.johnny.swapub.util.Logger

class ClubElectronicProductAdapter (val onClickListener: OnClickListener) :
    ListAdapter<Product, ClubElectronicProductAdapter.ClubElectronicProductViewHolder>(ClubElectronicProductViewHolder) {

    class ClubElectronicProductViewHolder(private var binding: ItemClubElectronicProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.propertyElectronicProduct = product
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }

        companion object DiffCallback : DiffUtil.ItemCallback<Product>() {

            fun from(parent: ViewGroup): ClubElectronicProductViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemClubElectronicProductBinding.inflate(layoutInflater, parent, false)

                return ClubElectronicProductViewHolder(binding)
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
    ): ClubElectronicProductViewHolder {
        return ClubElectronicProductViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ClubElectronicProductViewHolder, position: Int) {
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
