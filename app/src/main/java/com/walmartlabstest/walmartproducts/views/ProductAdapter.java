package com.walmartlabstest.walmartproducts.views;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.walmartlabstest.walmartproducts.R;
import com.walmartlabstest.walmartproducts.models.Product;
import com.walmartlabstest.walmartproducts.uiutils.StringUtils;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{

    private final Context context;
    private final ArrayList<Product> productList;
    private final StringUtils strUtils;
    CustomOnClickListener customClickListener;

    public ProductAdapter(Context context, ArrayList<Product> productList){
        
        this.context = context;
        this.productList = productList;
        strUtils = StringUtils.getInstance();
    }

    public interface CustomOnClickListener{

        public void onItemClick(View view,int position);
    }

    public void setClickListener(CustomOnClickListener customClickListener){

        this.customClickListener = customClickListener;
    }

    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View productCard = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card,parent,false);
        return new ViewHolder(productCard);
    }

    @Override
    public void onBindViewHolder(ProductAdapter.ViewHolder holder, int position) {

        Product product = productList.get(position);
        String name = product.getProductName();
        if(!strUtils.isNullEmpty(name)) {
            holder.tvTitle.setText(name);
        }
        String price = product.getPrice();
        if(!strUtils.isNullEmpty(price)){
            holder.tvPrice.setText(price);
        }
        String image = product.getProductImage();
        if(!strUtils.isNullEmpty(image)) {
            Picasso.with(context).load(image).placeholder(R.mipmap.ic_launcher).into(holder.ivImage);
        }
        Float reviewRating = product.getReviewRating();
        holder.ratingBar.setRating(reviewRating);
    }

    @Override
    public int getItemCount() {

        if(productList != null){

            return productList.size();
        }else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvTitle;
        public ImageView ivImage;
        public TextView tvPrice;
        public RatingBar ratingBar;

        public ViewHolder(View view){

            super(view);
            tvTitle = (TextView) view.findViewById(R.id.title);
            ivImage = (ImageView) view.findViewById(R.id.thumbnail);
            tvPrice = (TextView) view.findViewById(R.id.price);
            ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        customClickListener.onItemClick(view,position);
                    }
                }
            });
        }
    }
}
