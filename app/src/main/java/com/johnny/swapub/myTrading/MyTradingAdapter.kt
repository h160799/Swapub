package com.johnny.swapub.myTrading

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.johnny.swapub.data.Product
import com.johnny.swapub.databinding.ItemMyTradingBinding

class MyTradingAdapter(val onClickListener: OnClickListener) :
    ListAdapter<Product, MyTradingAdapter.MyTradingViewHolder>(MyTradingViewHolder) {

    class MyTradingViewHolder(private var binding: ItemMyTradingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.myTradingProperty = product
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }

        companion object DiffCallback : DiffUtil.ItemCallback<Product>() {

            fun from(parent: ViewGroup): MyTradingViewHolder {
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
        holder.bind(product)
    }

    class OnClickListener(val clickListener: (product: Product) -> Unit) {
        fun onClick(product: Product) = clickListener(product)
    }
}

