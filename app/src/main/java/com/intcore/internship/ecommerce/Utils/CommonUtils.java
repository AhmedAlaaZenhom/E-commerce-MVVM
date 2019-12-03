package com.intcore.internship.ecommerce.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.Settings;
import android.util.Patterns;
import android.util.TypedValue;

import com.intcore.internship.ecommerce.R;

public class CommonUtils {

    private CommonUtils(){
        // Class not instantiable
    }

    public static boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static float getPxFromDp( Context context , int dp){
        Resources r = context.getResources();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }
}
