package com.walmartlabstest.walmartproducts.models

import com.google.gson.annotations.SerializedName

data class ProductList(
    @SerializedName("products")
    val listProduct: List<Product>
)