package com.walmartlabstest.walmartproducts.uiutils;

public class StringUtils {

    public static final String products="products";

    private static StringUtils instance = null;
    public static String position ="positionSelected";

    public static StringUtils getInstance(){

        if(instance == null){

            instance = new StringUtils();
        }
        return instance;
    }

    private StringUtils(){

    }

    public boolean isNullEmpty(String text){

        if(text==null || text.isEmpty()){
            return true;
        }
        return false;
    }
}
