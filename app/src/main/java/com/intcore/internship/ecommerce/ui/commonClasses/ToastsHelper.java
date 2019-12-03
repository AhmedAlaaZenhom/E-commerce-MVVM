package com.intcore.internship.ecommerce.ui.commonClasses;

import android.content.Context;
import android.widget.Toast;

import com.intcore.internship.ecommerce.R;

public class ToastsHelper {

    private final Context mContext;

    public ToastsHelper(Context context) {
        mContext = context;
    }

    // Commons

    public void showMessageError(String message){
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    public void showConnectionError() {
        Toast.makeText(mContext, R.string.connection_error, Toast.LENGTH_SHORT).show();
    }

    // Login Activity

    public void showNotImplementedError(){
        Toast.makeText(mContext, R.string.not_implemented, Toast.LENGTH_SHORT).show();
    }

    public void showUnknownError() {
        Toast.makeText(mContext, R.string.unknown_error, Toast.LENGTH_SHORT).show();
    }

    public void showInValidEmailOrPasswordError() {
        Toast.makeText(mContext, R.string.invalid_email_or_password, Toast.LENGTH_SHORT).show();
    }
}
