package com.walmartlabstest.walmartproducts.views;

import android.content.Context;
import android.os.Build;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.text.Html;
import android.text.Spanned;
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

public class ProductsPagerAdapter extends PagerAdapter {

    private static final String TAG = ProductsPagerAdapter.class.getSimpleName();
    private final Context context;
    private final ArrayList<Product> productList;

    public ProductsPagerAdapter(Context context, ArrayList<Product> productList) {

        this.context = context;
        this.productList = productList;
    }

    @Override
    public int getCount() {

        if (productList != null)
            return productList.size();
        else
            return 0;
    }

    /**
     * Create the page for the given position.  The adapter is responsible
     * for adding the view to the container given here, although it only
     * must ensure this is done by the time it returns from
     * {@link #finishUpdate(android.view.ViewGroup)}.
     *
     * @param collection The containing View in which the page will be shown.
     * @param position   The page position to be instantiated.
     * @return Returns an Object representing the new page.  This does not
     * need to be a View, but can be some other container of the page.
     */
    @Override
    public Object instantiateItem(ViewGroup collection, int position) {

        Product product = productList.get(position);
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.activity_product_details_scr, collection, false);

        TextView tvTitle = (TextView) layout.findViewById(R.id.tvTitle);
        String name = product.getProductName();
        if (!StringUtils.getInstance().isNullEmpty(name))
            tvTitle.setText(name);

        RatingBar ratingBar = (RatingBar) layout.findViewById(R.id.ratingBar);
        float rating = product.getReviewRating();
        ratingBar.setRating(rating);

        TextView tvReviewCount = (TextView) layout.findViewById(R.id.tvReviewBy);
        long reviewCount = product.getReviewCount();
        String strReviewedBy = String.format(context.getString(R.string.reviewed_by), ""+reviewCount);
        tvReviewCount.setText(strReviewedBy);

        ImageView ivProduct = (ImageView) layout.findViewById(R.id.ivProdImg);
        String img = product.getProductImage();
        if (!StringUtils.getInstance().isNullEmpty(img))
            Picasso.with(context).load(img).into(ivProduct);

        TextView tvPrice = (TextView) layout.findViewById(R.id.tvPrice);
        String price = product.getPrice();
        if (!StringUtils.getInstance().isNullEmpty(price)) {

            tvPrice.setText(price);
        }

        TextView tvIsInStock = (TextView) layout.findViewById(R.id.tvInStock);
        boolean isInStock = product.isInStock();
        if(isInStock)
            tvIsInStock.setText(context.getString(R.string.available_in_stock));
        else
            tvIsInStock.setText(context.getString(R.string.not_in_stock));

        TextView tvShortDesc = (TextView) layout.findViewById(R.id.tvShortDesc);
        String shortDec = product.getShortDescription();
        if (!StringUtils.getInstance().isNullEmpty(shortDec)) {

            tvShortDesc.setVisibility(View.VISIBLE);
            Spanned spannedString = fromHtml(shortDec);
            tvShortDesc.setText(spannedString);
        }else{
            tvShortDesc.setVisibility(View.GONE);
        }

        TextView tvLongDesc = (TextView) layout.findViewById(R.id.tvLongDesc);
        String longDec = product.getLongDescription();
        if (!StringUtils.getInstance().isNullEmpty(longDec)) {
            Spanned spannedString = fromHtml(longDec);
            tvLongDesc.setText(spannedString);
        }

        collection.addView(layout);
        return layout;
    }

    /**
     * Remove a page for the given position.  The adapter is responsible
     * for removing the view from its container, although it only must ensure
     * this is done by the time it returns from {@link #finishUpdate(android.view.ViewGroup)}.
     *
     * @param collection The containing View from which the page will be removed.
     * @param position   The page position to be removed.
     * @param view       The same object that was returned by
     *                   {@link #instantiateItem(android.view.View, int)}.
     */
    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }


    /**
     * Determines whether a page View is associated with a specific key object
     * as returned by instantiateItem(ViewGroup, int). This method is required
     * for a PagerAdapter to function properly.
     *
     * @param view   Page View to check for association with object
     * @param object Object to check for association with view
     * @return
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }


    /**
     * Called when the a change in the shown pages has been completed.  At this
     * point you must ensure that all of the pages have actually been added or
     * removed from the container as appropriate.
     *
     * @param viewGroup The containing View which is displaying this adapter's
     *                  page views.
     */
    @Override
    public void finishUpdate(ViewGroup viewGroup) {
    }


    @Override
    public void restoreState(Parcelable parcelable, ClassLoader classLoader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void startUpdate(ViewGroup viewGroup) {
    }

    @SuppressWarnings("deprecation")
    private static Spanned fromHtml(String source) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(source);

        }
    }
}