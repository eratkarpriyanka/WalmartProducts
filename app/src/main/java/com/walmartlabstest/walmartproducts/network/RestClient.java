package com.walmartlabstest.walmartproducts.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {

    public RestClient(){

    }

    public IWalmartsApi createService(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IWalmartsApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IWalmartsApi apiInterface = retrofit.create(IWalmartsApi.class);
        return apiInterface;
    }
}