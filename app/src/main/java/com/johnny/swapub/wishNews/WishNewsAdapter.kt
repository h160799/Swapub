package com.johnny.swapub.wishNews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.johnny.swapub.bindImage
import com.johnny.swapub.data.Product
import com.johnny.swapub.data.User
import com.johnny.swapub.databinding.ItemWishNewsGridBinding


class WishNewsAdapter(val onClickListener: OnClickListener) :
        ListAdapter<Product, WishNewsAdapter.WishNewsViewHolder>(WishNewsViewHolder) {

    class WishNewsViewHolder(private var binding: ItemWishNewsGridBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            var user: User
            FirebaseFirestore.getInstance()
                    .collection("user")
                    .whereEqualTo("id", product.user)
                    .get()
                    .addOnSuccessListener {
                        user = it.toObjects(User::class.java)[0]

                        binding.wishPageUserName.text = user.name
                        bindImage(binding.wishPageUserHead, user.image)
                    }

            binding.wishNewsProduct = product
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }

        companion object DiffCallback : DiffUtil.ItemCallback<Product>() {

            fun from(parent: ViewGroup): WishNewsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemWishNewsGridBinding.inflate(layoutInflater, parent, false)

                return WishNewsViewHolder(binding)
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
    ): WishNewsViewHolder {
        return WishNewsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: WishNewsViewHolder, position: Int) {
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

