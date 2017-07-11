package com.walmartlabstest.walmartproducts.network;

import com.walmartlabstest.walmartproducts.models.ResponseGetProducts;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IWalmartsApi {

    String BASE_URL = "https://walmartlabs-test.appspot.com/_ah/api/walmart/v1/";

    @GET("walmartproducts/{apiKey}/{pgNum}/{pgSize}")
    Call<ResponseGetProducts> getProductsApi(@Path("apiKey") String apiKey, @Path("pgNum") long pageNumber, @Path("pgSize") long pageSize);
}