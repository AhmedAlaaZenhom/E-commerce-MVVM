package com.intcore.internship.ecommerce.ui.commonClasses;

import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import androidx.annotation.Nullable;

public class PicassoHelper {

    private static final String TAG = PicassoHelper.class.getSimpleName();

    public interface Listener {
        void onSuccess();

        void onFailure();
    }

    public static final int JUST_FIT = 0, CENTER_CROP = 1, CENTER_INSIDE = 2;

    public static final String STORAGE_BASE_URL = "https://e-commerce-dev.intcore.net/";


    public static void loadImageWithCache(
            final String url, ImageView imageView, int mode, @Nullable Integer errorResID, @Nullable Listener listener) {
        String imageUrl = TextUtils.isEmpty(url) ? "fakeUrl" : url;

        RequestCreator requestCreator = Picasso.get()
                .load(imageUrl)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .fit();

        if (mode == CENTER_CROP)
            requestCreator = requestCreator.centerCrop();
        else if (mode == CENTER_INSIDE)
            requestCreator = requestCreator.centerInside();

        requestCreator.into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                if (listener != null)
                    listener.onSuccess();
            }

            @Override
            public void onError(Exception e) {
                loadImageSkippingCache(imageUrl, imageView, mode, errorResID, listener);
            }
        });
    }


    // Access can be public
    private static void loadImageSkippingCache(
            final String url, ImageView imageView, int mode, @Nullable Integer errorResID, @Nullable Listener listener) {
        String imageUrl = TextUtils.isEmpty(url) ? "fakeUrl" : url;

        RequestCreator requestCreator = Picasso.get()
                .load(imageUrl)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .fit();

        if (mode == CENTER_CROP)
            requestCreator = requestCreator.centerCrop();
        else if (mode == CENTER_INSIDE)
            requestCreator = requestCreator.centerInside();

        if (errorResID != null)
            requestCreator = requestCreator.error(errorResID);
        else
            requestCreator = requestCreator.error(new ColorDrawable(0xFFFFFF));

        requestCreator.into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                if (listener != null)
                    listener.onSuccess();
            }

            @Override
            public void onError(Exception e) {
                if (listener != null)
                    listener.onFailure();
            }
        });
    }

}
