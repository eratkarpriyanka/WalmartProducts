package com.walmartlabstest.walmartproducts.models

data class Product(
    val productId: String,
    val productName: String,
    val shortDescription: String,
    val longDescription: String,
    val price: String,
    val productImage: String,
    val reviewRating: Float,
    val reviewCount: Long,
    val inStock: Boolean,
)