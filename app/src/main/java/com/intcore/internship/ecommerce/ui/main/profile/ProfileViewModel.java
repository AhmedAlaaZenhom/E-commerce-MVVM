package com.intcore.internship.ecommerce.ui.main.profile;

import android.app.Application;
import android.util.Log;

import com.intcore.internship.ecommerce.R;
import com.intcore.internship.ecommerce.data.DataManager;
import com.intcore.internship.ecommerce.data.remote.helperModels.profile.ProfileResponseModel;
import com.intcore.internship.ecommerce.ui.baseClasses.BaseViewModel;
import com.intcore.internship.ecommerce.ui.commonClasses.ToastsHelper;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProfileViewModel extends BaseViewModel {

    private final static String TAG = ProfileViewModel.class.getSimpleName() ;

    private MutableLiveData<ProfileResponseModel> profileResponseLD ;
    MutableLiveData<ProfileResponseModel> getProfileResponseLD() {
        if (profileResponseLD == null)
            profileResponseLD = new MutableLiveData<>();
        return profileResponseLD;
    }

    private DataManager dataManager ;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        dataManager = getCompositionRoot().getDataManager() ;
    }

    void getProfile() {
        if (dataManager.isUserLoggedIn()) {
            Log.d(TAG,"Starting GetProfile call ...") ;
            getCompositeDisposable().add(dataManager
                    .getProfile()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        if (response.isSuccessful()) {
                            Log.d(TAG,"GetProfile Success") ;
                            getProfileResponseLD().setValue(response.body());
                        } else {
                            Log.d(TAG, "GetProfile failure: "+response.errorBody().string());
                        }
                    }, throwable -> {
                        Log.d(TAG, "GetProfile throwable: "+throwable.getMessage());
                        setToastMessagesLD(new ToastsHelper.ToastMessage(getApplication().getString(R.string.connection_error),ToastsHelper.MESSAGE_TYPE_ERROR));
                    })
            );
        }
    }

    String getSavedLocale() {
        return dataManager.getSavedLocale();
    }

    void changeLocale(String locale) {
        dataManager.setCurrentLocale(locale);
    }
}
