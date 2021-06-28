package com.walmartlabstest.walmartproducts.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.walmartlabstest.walmartproducts.R
import com.walmartlabstest.walmartproducts.models.Product
import com.walmartlabstest.walmartproducts.models.ResponseGetProducts
import com.walmartlabstest.walmartproducts.network.RestClient
import com.walmartlabstest.walmartproducts.uiutils.EndlessRecyclerViewScrollListener
import com.walmartlabstest.walmartproducts.uiutils.StringUtils
import com.walmartlabstest.walmartproducts.views.ProductAdapter.CustomOnClickListener
import com.walmartlabstest.walmartproducts.views.ProductListScr
import org.parceler.Parcels
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class ProductListScr : AppCompatActivity(), CustomOnClickListener {
    private var progressView: ProgressBar? = null
    private var tvEmptyList: TextView? = null
    private var recyclerView: RecyclerView? = null
    private var scrollListener: EndlessRecyclerViewScrollListener? = null
    private var productList: ArrayList<Product>? = null
    private var adapter: ProductAdapter? = null
    private var toolbar: Toolbar? = null
    private var stringUtils: StringUtils? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_product_list)
        toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        getProducts(1)
        setViews()
    }

    private fun setViews() {
        progressView = findViewById<View>(R.id.progressBar) as ProgressBar
        tvEmptyList = findViewById<View>(R.id.tvEmptyList) as TextView
        recyclerView = findViewById<View>(R.id.recyclerView) as RecyclerView
        stringUtils = StringUtils.getInstance()
    }

    fun getProducts(page: Int) {
        val client = RestClient()
        val walmartsApi = client.createService()
        val call = walmartsApi.getProductsApi(page.toLong(), 10)
        call.enqueue(object : Callback<ResponseGetProducts?> {
            override fun onResponse(
                call: Call<ResponseGetProducts?>,
                response: Response<ResponseGetProducts?>
            ) {
                if (response != null) {
                    if (response.isSuccessful) {
                        val responseProducts = response.body()
                        productList = responseProducts!!.productArrayList
                        loadViews()
                    }
                } else {
                    Toast.makeText(
                        this@ProductListScr,
                        getString(R.string.no_products),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponseGetProducts?>, t: Throwable) {
                Log.e(TAG, "Failure in getting response : " + t.message + " " + t.cause)
            }
        })
    }

    private fun loadViews() {
        tvEmptyList!!.visibility = View.GONE
        recyclerView!!.visibility = View.VISIBLE
        val staggeredGridLayout = StaggeredGridLayoutManager(2, 1)
        //   recyclerView.hasFixedSize();
        recyclerView!!.layoutManager = staggeredGridLayout
        recyclerView!!.itemAnimator = DefaultItemAnimator()
        scrollListener = object : EndlessRecyclerViewScrollListener(staggeredGridLayout) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {

                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                view.post { loadMoreProducts(page) }
            }
        }
        recyclerView!!.addOnScrollListener(scrollListener)
        adapter = ProductAdapter(this, productList)
        adapter!!.setClickListener(this)
        recyclerView!!.adapter = adapter
    }

    private fun loadMoreProducts(page: Int) {
        getProducts(page)
    }

    fun showProgressBar() {
        // Show progress item
        if (progressView != null) {
            progressView!!.visibility = View.VISIBLE
        }
    }

    fun hideProgressBar() {
        // Hide progress item
        if (progressView != null) {
            progressView!!.visibility = View.GONE
        }
    }

    override fun onItemClick(view: View, position: Int) {
        val product = productList!![position]
        val intent = Intent(this, ProductDetailsScr::class.java)
        intent.putExtra(StringUtils.products, Parcels.wrap(productList))
        intent.putExtra(StringUtils.position, position)
        startActivity(intent)
    }

    companion object {
        private val TAG = ProductListScr::class.java.simpleName
    }
}