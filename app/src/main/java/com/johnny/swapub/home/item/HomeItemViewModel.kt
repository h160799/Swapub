package com.johnny.swapub.home.item

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.johnny.swapub.data.Club
import com.johnny.swapub.data.Message
import com.johnny.swapub.data.Product
import com.johnny.swapub.data.User
import com.johnny.swapub.data.remote.SwapubRepository
import com.johnny.swapub.util.Logger

class HomeItemViewModel(val homeTypeFilter: SwapubRepository) : ViewModel() {

    private val _itemInfo = MutableLiveData<List<Product>>()
    val itemInfo: LiveData<List<Product>>
        get() = _itemInfo


    private val _navigateToSelecteditemInfo = MutableLiveData<Product>()
    val navigateToSelecteditemInfo: MutableLiveData<Product>
        get() = _navigateToSelecteditemInfo





    fun displayItemProductDetails(product: Product) {
        _navigateToSelecteditemInfo.value = product
    }

    fun displayItemProductDetailsComplete() {
        _navigateToSelecteditemInfo.value = null
    }

    val product = FirebaseFirestore.getInstance()
        .collection("product")

    fun addData() {
        val document = product.document()
        val data = Product(
            user = "",
            name = "",





            ownerId = "",
            ownerName = "",
            ownerImage = "",
            productId = "",
            senderId = "",
            senderName = "Ni A Yi",
            senderImage = "https://cf.shopee.tw/file/8a9e53d639fc0e77e09dc7b608b48172",
            text = Message(
                asker = "",
                responser = "你那個東西很爛ㄋㄟ ",
                time = 0

            )
        )
        document.set(data)
    }

    private fun getData() {

        product
            .get()
            .addOnSuccessListener { result ->
                val listProduct = mutableListOf<Product>()
                for (document in result) {
                    val product = document.toObject(Product::class.java)

                    listProduct.add(product)
                    Logger.d("333$listProduct")
                }

                _itemInfo.value = listProduct
            }
    }

    init {
    }

}