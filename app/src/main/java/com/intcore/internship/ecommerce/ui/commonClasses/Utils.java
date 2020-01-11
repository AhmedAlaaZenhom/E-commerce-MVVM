package com.intcore.internship.ecommerce.ui.commonClasses;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;

public class Utils {

    private Utils() {
        // non-instantiable class
    }

    public static DisplayMetrics getDisplayMetrics(@NonNull Activity activity){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics ;
    }

    public static float convertDpToPixel(Context context, float dp){
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}
