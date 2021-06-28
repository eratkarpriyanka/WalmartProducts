package com.walmartlabstest.walmartproducts.network

import com.walmartlabstest.walmartproducts.models.ResponseGetProducts
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

class ProductRepository constructor(private val restClient: RestClient) {
    fun getProductsApi(pageNumber: Long, pageSize: Long): Call<ResponseGetProducts?> {
        return restClient.getProductsApi(pageNumber, pageSize)
    }

    companion object {
        const val BASE_URL = "https://mobile-tha-server.firebaseapp.com/"
    }
}