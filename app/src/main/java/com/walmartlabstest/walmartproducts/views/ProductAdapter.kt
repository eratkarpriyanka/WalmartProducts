package com.walmartlabstest.walmartproducts.views

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.walmartlabstest.walmartproducts.R
import com.walmartlabstest.walmartproducts.models.Product
import java.util.*

class ProductAdapter(private val context: Context, private val productList: ArrayList<Product>?) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    var customClickListener: CustomOnClickListener? = null

    interface CustomOnClickListener {
        fun onItemClick(view: View?, position: Int)
    }

    fun setClickListener(customClickListener: CustomOnClickListener?) {
        this.customClickListener = customClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val productCard =
            LayoutInflater.from(parent.context).inflate(R.layout.product_card, parent, false)
        return ViewHolder(productCard)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = productList!![position]
        val name = product.productName
        if (!name.isEmpty()) {
            holder.tvTitle.text = name
        }
        val price = product.price
        if (!price.isEmpty()) {
            holder.tvPrice.text = price
        }
        var image = product.productImage
        if (!image.isEmpty()) {
            image = IMAGE_BASE_URL + image
            Picasso.get().load(image).placeholder(R.mipmap.ic_launcher).into(holder.ivImage)
        }
        val reviewRating = product.reviewRating
        holder.ratingBar.rating = reviewRating
    }

    override fun getItemCount(): Int {
        return productList?.size ?: 0
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvTitle: TextView
        var ivImage: ImageView
        var tvPrice: TextView
        var ratingBar: RatingBar

        init {
            tvTitle = view.findViewById<View>(R.id.title) as TextView
            ivImage = view.findViewById<View>(R.id.thumbnail) as ImageView
            tvPrice = view.findViewById<View>(R.id.price) as TextView
            ratingBar = view.findViewById<View>(R.id.ratingBar) as RatingBar
            view.setOnClickListener { view ->
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    customClickListener!!.onItemClick(view, position)
                }
            }
        }
    }
}