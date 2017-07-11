package com.walmartlabstest.walmartproducts.views;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.walmartlabstest.walmartproducts.R;
import com.walmartlabstest.walmartproducts.models.Product;
import com.walmartlabstest.walmartproducts.uiutils.StringUtils;

import org.parceler.Parcels;

import java.util.ArrayList;

public class ProductDetailsScr extends AppCompatActivity {

    private ArrayList<Product> productList;
    private ViewPager productPager;
    private ProductsPagerAdapter productAdapter;
    private Toolbar toolbar;
    private int selectedDefaultPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products_pager);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        productList = (ArrayList<Product>) Parcels.unwrap(getIntent().getParcelableExtra(StringUtils.products));
        selectedDefaultPos = getIntent().getIntExtra(StringUtils.position,0);
        productAdapter = new ProductsPagerAdapter(this,productList);
        productPager = (ViewPager) findViewById(R.id.productPager);
        productPager.setAdapter(productAdapter);
        productPager.setCurrentItem(selectedDefaultPos);
    }
}
