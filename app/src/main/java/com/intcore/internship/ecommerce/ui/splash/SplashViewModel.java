package com.intcore.internship.ecommerce.ui.splash;

import android.app.Application;

import com.intcore.internship.ecommerce.ui.baseClasses.BaseViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

public class SplashViewModel extends BaseViewModel {

    public SplashViewModel(@NonNull Application application) {
        super(application);
    }

    boolean isUserLoggedIn(){
        return getCompositionRoot().getDataManager().isUserLoggedIn();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
