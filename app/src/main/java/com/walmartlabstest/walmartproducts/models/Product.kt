package com.walmartlabstest.walmartproducts.models

import org.parceler.Parcel

@Parcel
class Product {
    var IMAGE_BASE_URL = "https://mobile-tha-server.firebaseapp.com/"
    var productId: String? = null
    var productName: String? = null
    var shortDescription: String? = null
    var longDescription: String? = null
    var price: String? = null
    var productImage: String? = null
    var reviewRating = 0f
    var reviewCount: Long = 0
    var isInStock = false
    fun getProductImage(): String {
        return IMAGE_BASE_URL + productImage
    }

    fun setProductImage(productImage: String?) {
        this.productImage = productImage
    }
}