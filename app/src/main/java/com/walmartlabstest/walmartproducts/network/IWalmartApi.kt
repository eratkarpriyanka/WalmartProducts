package com.walmartlabstest.walmartproducts.network

import com.walmartlabstest.walmartproducts.models.ProductList
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface IWalmartApi {

    @GET("walmartproducts/{pgNum}/{pgSize}")
    suspend fun getProductsApi(
        @Path("pgNum") pageNumber: Int,
        @Path("pgSize") pageSize: Int
    ): Response<ProductList>

    companion object {
        const val BASE_URL = "https://mobile-tha-server.firebaseapp.com/"

        private val logger =
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

        private val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()

        fun createService(): IWalmartApi {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(IWalmartApi::class.java)
        }
    }
}