package com.walmartlabstest.walmartproducts.network

import com.walmartlabstest.walmartproducts.models.ProductList
import retrofit2.Response

class ProductRepository constructor(private val IWalmartApi: IWalmartApi) {
    suspend fun getProductsApi(pageNumber: Int, pageSize: Int): Response<ProductList> {
        return IWalmartApi.getProductsApi(pageNumber, pageSize)
    }
}