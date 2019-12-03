package com.intcore.internship.ecommerce.ui.splash;

import android.app.Application;

import com.intcore.internship.ecommerce.ui.baseClasses.BaseViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

public class SplashViewModel extends BaseViewModel {

    private MutableLiveData<Boolean> isLoggedInMood ;

    MutableLiveData<Boolean> getIsLoggedInMood() {
        if(isLoggedInMood==null)
            isLoggedInMood = new MutableLiveData<>() ;
        return isLoggedInMood;
    }

    public SplashViewModel(@NonNull Application application) {
        super(application);
    }

    void decideNextActivity(){
        getIsLoggedInMood().setValue(getCompositionRoot().getDataManager().isUserLoggedIn());
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
