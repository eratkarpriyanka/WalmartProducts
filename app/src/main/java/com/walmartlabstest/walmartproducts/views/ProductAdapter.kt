package com.walmartlabstest.walmartproducts.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.walmartlabstest.walmartproducts.R
import com.walmartlabstest.walmartproducts.models.Product
import com.walmartlabstest.walmartproducts.uiutils.IMAGE_BASE_URL

class ProductAdapter :
    PagedListAdapter<Product, ProductAdapter.ViewHolder>(PRODUCT_DIFF_CALLBACK) {

    private var productList = mutableListOf<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val productCard =
            LayoutInflater.from(parent.context).inflate(R.layout.product_card, parent, false)
        return ViewHolder(productCard)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = getItem(position)
        product?.let { holder.bindProduct(it) }
        /* val product = productList[position]
         val name = product.productName
         holder.tvTitle.text = name

         val price = product.price
         holder.tvPrice.text = price

         val reviewRating = product.reviewRating
         holder.ratingBar.rating = reviewRating

         var image = product.productImage
         if (image.isNotEmpty()) {
             image = IMAGE_BASE_URL + image
             Picasso.get().load(image).placeholder(R.mipmap.ic_launcher).into(holder.ivImage)
         }*/
    }

    /*override fun getItemCount(): Int {
        return productList.size
    }*/

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvTitle = view.findViewById(R.id.title) as TextView
        var ivImage = view.findViewById(R.id.thumbnail) as ImageView
        var tvPrice = view.findViewById(R.id.price) as TextView
        var ratingBar = view.findViewById(R.id.ratingBar) as RatingBar

        fun bindProduct(product: Product) {
            with(product) {
                val name = product.productName
                tvTitle.text = name

                val price = product.price
                tvPrice.text = price

                val reviewRating = product.reviewRating
                ratingBar.rating = reviewRating

                var image = product.productImage
                if (image.isNotEmpty()) {
                    image = IMAGE_BASE_URL + image
                    Picasso.get().load(image).placeholder(R.mipmap.ic_launcher).into(ivImage)
                }
            }
        }
    }

    fun loadCourseList(productList: List<Product>) {
        this.productList = productList.toMutableList()
        notifyDataSetChanged()
    }

    companion object {
        private val PRODUCT_DIFF_CALLBACK = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldProduct: Product, newProduct: Product) =
                oldProduct.productId == newProduct.productId

            override fun areContentsTheSame(
                oldProduct: Product,
                newProduct: Product
            ) = oldProduct == newProduct
        }
    }
}