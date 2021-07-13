package com.walmartlabstest.walmartproducts.views

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.walmartlabstest.walmartproducts.models.Product
import com.walmartlabstest.walmartproducts.network.IWalmartApi
import kotlinx.coroutines.*


class ProductDataSource : PageKeyedDataSource<String, Product>() {

    private val apiService = IWalmartApi.createService()
    var job: Job? = null
    val loading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    private fun onError(message: String) {
        errorMessage.postValue(message)
    }

    fun getRequestFailureLiveData(): LiveData<String> {
        return errorMessage
    }

    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, Product>
    ) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            try {
                val response =
                    apiService.getProductsApi(1, 10)
                withContext(Dispatchers.Main) {
                    when {
                        response.isSuccessful -> {
                            val listProducts = response.body()?.listProduct
                            callback.onResult(
                                listProducts ?: listOf(),
                                null,
                                (listProducts?.size?.plus(1)).toString()
                            )
                        }
                        else -> {
                            loading.value = false
                            onError("Error : ${response.message()} ")
                        }
                    }

                }
            } catch (exception: Exception) {
                Log.e("PostsDataSource", "Failed to fetch data! ${exception.message}")
                onError("Error : ${exception.message} ")
            }

        }

    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, Product>) {

        val start = params.key.toInt() * params.requestedLoadSize

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            try {
                val response = apiService.getProductsApi(
                    params.key.toInt(),
                    pageSize = params.requestedLoadSize
                )
                withContext(Dispatchers.Main) {
                    when {
                        response.isSuccessful -> {
                            val listProducts = response.body()?.listProduct
                            callback.onResult(
                                listProducts ?: listOf(),
                                (listProducts?.size?.plus(params.key.toInt())).toString()
                            )
                        }
                        else -> {
                            loading.value = false
                            onError("Error : ${response.message()} ")
                        }
                    }

                }
            } catch (exception: Exception) {
                Log.e("PostsDataSource", "Failed to fetch data! ${exception.message}")
                onError("Error : ${exception.message} ")
            }
        }

    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, Product>) {

    }

    override fun invalidate() {
        super.invalidate()
        job?.cancel()
    }

}