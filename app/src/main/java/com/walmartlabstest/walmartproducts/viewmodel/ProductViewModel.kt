package com.walmartlabstest.walmartproducts.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.walmartlabstest.walmartproducts.models.Product
import com.walmartlabstest.walmartproducts.views.ProductDataSource
import kotlinx.coroutines.Dispatchers

class ProductViewModel() : ViewModel() {
    var productLiveData: LiveData<PagedList<Product>>

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(10)
            .setEnablePlaceholders(false)
            .build()
        productLiveData =
            initializedPagedListBuilder(config).build()
    }

    private fun initializedPagedListBuilder(config: PagedList.Config):
            LivePagedListBuilder<String, Product> {
        val dataSourceFactory = object : DataSource.Factory<String, Product>() {
            override fun create(): DataSource<String, Product> {
                return ProductDataSource()
            }
        }
        return LivePagedListBuilder(dataSourceFactory, config)
    }

    fun getWalmartProducts(): LiveData<PagedList<Product>> = productLiveData
}