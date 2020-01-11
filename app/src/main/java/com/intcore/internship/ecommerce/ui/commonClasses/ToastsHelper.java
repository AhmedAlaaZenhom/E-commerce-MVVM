package com.intcore.internship.ecommerce.ui.commonClasses;

import android.content.Context;

import com.intcore.internship.ecommerce.R;

import androidx.annotation.NonNull;
import es.dmoral.toasty.Toasty;

public class ToastsHelper {

    public static final int MESSAGE_TYPE_SUCCESS = 0;
    public static final int MESSAGE_TYPE_INFO = 1;
    public static final int MESSAGE_TYPE_WARNING = 2;
    public static final int MESSAGE_TYPE_ERROR = 3;

    public static class ToastMessage {
        private String message;
        private int messageType;

        public ToastMessage(String message, int messageType) {
            this.message = message;
            this.messageType = messageType;
        }
    }

    private final Context mContext;

    public ToastsHelper(Context context) {
        mContext = context;
        Toasty.Config.getInstance()
                .allowQueue(false)
                .apply();
    }

    public void showMessage(@NonNull ToastMessage toastMessage){
        switch (toastMessage.messageType){
            case MESSAGE_TYPE_SUCCESS:
                showSuccessMessage(toastMessage.message);
                break;
            case MESSAGE_TYPE_INFO:
                showInfoMessage(toastMessage.message);
                break;
            case MESSAGE_TYPE_WARNING:
                showWarningMessage(toastMessage.message);
                break;
            case MESSAGE_TYPE_ERROR:
                showErrorMessage(toastMessage.message);
                break;
        }
    }


    public void showConnectionError() {
        showErrorMessage(mContext.getString(R.string.connection_error));
    }

    public void showUnknownError() {
        showWarningMessage(mContext.getString(R.string.unknown_error));
    }


    private void showErrorMessage(String message){
        Toasty.error(mContext,message,Toasty.LENGTH_SHORT,true).show();
    }

    private void showSuccessMessage(String message){
        Toasty.success(mContext,message,Toasty.LENGTH_SHORT,true).show();
    }

    private void showInfoMessage(String message){
        Toasty.info(mContext,message,Toasty.LENGTH_SHORT,true).show();
    }

    private void showWarningMessage(String message){
        Toasty.warning(mContext,message,Toasty.LENGTH_SHORT,true).show();
    }

}
