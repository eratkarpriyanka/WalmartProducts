package com.walmartlabstest.walmartproducts.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.walmartlabstest.walmartproducts.R;
import com.walmartlabstest.walmartproducts.models.Product;
import com.walmartlabstest.walmartproducts.models.ResponseGetProducts;
import com.walmartlabstest.walmartproducts.network.IWalmartsApi;
import com.walmartlabstest.walmartproducts.network.RestClient;
import com.walmartlabstest.walmartproducts.uiutils.EndlessRecyclerViewScrollListener;
import com.walmartlabstest.walmartproducts.uiutils.StringUtils;

import org.parceler.Parcels;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListScr extends AppCompatActivity implements ProductAdapter.CustomOnClickListener {

    private static final String TAG = ProductListScr.class.getSimpleName();
    private ProgressBar progressView;
    private TextView tvEmptyList;
    private RecyclerView recyclerView;
    private EndlessRecyclerViewScrollListener scrollListener;
    private ArrayList<Product> productList;
    private ProductAdapter adapter;
    private Toolbar toolbar;
    private StringUtils stringUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_product_list);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getProducts(0);
        setViews();
    }

    private void setViews() {

        progressView = (ProgressBar) findViewById(R.id.progressBar);
        tvEmptyList = (TextView) findViewById(R.id.tvEmptyList);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        stringUtils = StringUtils.getInstance();
    }

    public void getProducts(int page) {

        RestClient client = new RestClient();
        IWalmartsApi walmartsApi = client.createService();
        Call<ResponseGetProducts> call = walmartsApi.getProductsApi("c86bbded-3988-463f-94a5-6443ed7cec34",page,24);
        call.enqueue(new Callback<ResponseGetProducts>() {
            @Override
            public void onResponse(Call<ResponseGetProducts> call, Response<ResponseGetProducts> response) {

                if(response!=null) {

                    if(response.isSuccessful()) {
                        ResponseGetProducts responseProducts = response.body();
                        productList = responseProducts.getProductArrayList();
                        loadViews();
                    }
                }else{

                    Toast.makeText(ProductListScr.this,getString(R.string.no_products),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseGetProducts> call, Throwable t) {

                Log.e(TAG,"Failure in getting response : "+t.getMessage()+" "+t.getCause());
            }
        });
    }

    private void loadViews() {

        tvEmptyList.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        StaggeredGridLayoutManager staggeredGridLayout= new StaggeredGridLayoutManager(2,1);
        //   recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(staggeredGridLayout);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        scrollListener = new EndlessRecyclerViewScrollListener(staggeredGridLayout){

            @Override
            public void onLoadMore(final int page, int totalItemsCount, RecyclerView view) {

                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                view.post(new Runnable() {
                    @Override
                    public void run() {

                        loadMoreProducts(page);
                    }
                });

            }
        };
        recyclerView.addOnScrollListener(scrollListener);
        adapter = new ProductAdapter(this,productList);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

    }

    private void loadMoreProducts(int page) {

        getProducts(page);
    }

    public void showProgressBar() {
        // Show progress item
        if( progressView!=null) {
            progressView.setVisibility(View.VISIBLE);
        }
    }

    public void hideProgressBar() {
        // Hide progress item

        if( progressView!=null) {
            progressView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClick(View view, int position) {

        Product product = productList.get(position);
        Intent intent = new Intent(this,ProductDetailsScr.class);
        intent.putExtra(StringUtils.products, Parcels.wrap(productList));
        intent.putExtra(StringUtils.position, position);
        startActivity(intent);
    }
}
