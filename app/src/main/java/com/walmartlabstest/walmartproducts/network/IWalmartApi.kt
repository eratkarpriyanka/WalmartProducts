package com.walmartlabstest.walmartproducts.network

import com.walmartlabstest.walmartproducts.models.ResponseGetProducts
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface RestClient {

    @GET("walmartproducts/{pgNum}/{pgSize}")
    fun getProductsApi(
        @Path("pgNum") pageNumber: Long,
        @Path("pgSize") pageSize: Long
    ): Call<ResponseGetProducts?>?

    companion object {
        const val BASE_URL = "https://mobile-tha-server.firebaseapp.com/"

        private val logger =
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

        private val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()

        fun createService(): IWalmartsApi {
            val retrofit = Retrofit.Builder()
                .baseUrl(IWalmartsApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(IWalmartsApi::class.java)
        }
    }
}