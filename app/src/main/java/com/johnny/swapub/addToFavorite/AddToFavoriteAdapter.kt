package com.johnny.swapub.addToFavorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.johnny.swapub.R
import com.johnny.swapub.data.Product

class AddToFavoriteAdapter(private var products: List<Product> = mutableListOf(),val viewModel: AddToFavoriteViewModel
) : RecyclerView.Adapter<AddToFavoriteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_product, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        holder.name.text = product.productTitle
        holder.city.text = product.location?.countries?.get(0)?.cities?.get(0)?.name
        viewModel.productId.value = products[position].id
        Glide.with(holder.image)
            .load(product.productImage?.get(0))
            .into(holder.image)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun setProducts(products: List<Product>) {
        this.products = products
    }

    fun getProducts(): List<Product> {
        return products
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.item_name)
        var city: TextView = view.findViewById(R.id.item_city)
        var image: ImageView = view.findViewById(R.id.item_image)
    }


}