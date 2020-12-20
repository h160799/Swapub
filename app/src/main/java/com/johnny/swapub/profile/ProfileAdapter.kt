package com.johnny.swapub.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.johnny.swapub.R
import com.johnny.swapub.SwapubApplication
import com.johnny.swapub.bindImage
import com.johnny.swapub.data.LoadApiStatus
import com.johnny.swapub.data.Product
import com.johnny.swapub.data.User
import com.johnny.swapub.databinding.ItemMyWishGridBinding
import kotlinx.coroutines.launch

class ProfileAdapter (val onClickListener: OnClickListener) :
        ListAdapter<Product, ProfileAdapter.ProfileViewHolder>(ProfileViewHolder) {

        class ProfileViewHolder(private var binding: ItemMyWishGridBinding) :
            RecyclerView.ViewHolder(binding.root) {

            fun bind(product: Product) {
                var user : User
                FirebaseFirestore.getInstance()
                    .collection("user")
                    .whereEqualTo("id", product.user)
                    .get()
                    .addOnSuccessListener {
                        user = it.toObjects(User::class.java)[0]
                        binding.userName.text = user.name
                        bindImage(binding.imageUserHead, user.image)
                    }



                binding.myWishProduct = product
                // This is important, because it forces the data binding to execute immediately,
                // which allows the RecyclerView to make the correct view size measurements
                binding.executePendingBindings()
            }

            companion object DiffCallback : DiffUtil.ItemCallback<Product>() {

                fun from(parent: ViewGroup): ProfileViewHolder {
                    val layoutInflater = LayoutInflater.from(parent.context)
                    val binding = ItemMyWishGridBinding.inflate(layoutInflater, parent, false)

                    return ProfileViewHolder(binding)
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
        ): ProfileViewHolder {
            return ProfileViewHolder.from(parent)
        }

        override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
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
