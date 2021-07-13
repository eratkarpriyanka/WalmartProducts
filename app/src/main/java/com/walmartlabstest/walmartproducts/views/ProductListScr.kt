package com.walmartlabstest.walmartproducts.views

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.walmartlabstest.walmartproducts.R
import com.walmartlabstest.walmartproducts.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.screen_product_list.*

class ProductListScr : AppCompatActivity() {

    private lateinit var viewModel: ProductViewModel
    private val adapter: ProductAdapter = ProductAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_product_list)
        viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        loadViews()
        observerViewModels()
    }

    private fun observerViewModels() {
        showProgressBar()
        viewModel.getWalmartProducts().observe(this, { pagedListProducts ->
            hideProgressBar()
            hideEmptyView()
            adapter.submitList(pagedListProducts)
        })

        viewModel.getWalmartProductsFailure().observe(this, { errorMessage ->
            Toast.makeText(this, "Error retrieving products !!!", Toast.LENGTH_SHORT).show()
        })
    }

    private fun loadViews() {
        val gridLayoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = adapter
        setEmptyView()
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    private fun setEmptyView() {
        tvEmptyList.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }

    private fun hideEmptyView() {
        tvEmptyList.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }
}