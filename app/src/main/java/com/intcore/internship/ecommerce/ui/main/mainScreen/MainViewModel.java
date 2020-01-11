package com.intcore.internship.ecommerce.ui.main.mainScreen;

import android.app.Application;
import android.util.Log;

import com.intcore.internship.ecommerce.R;
import com.intcore.internship.ecommerce.data.DataManager;
import com.intcore.internship.ecommerce.data.remote.helperModels.cart.CartsResponseModel;
import com.intcore.internship.ecommerce.data.remote.helperModels.profile.ProfileResponseModel;
import com.intcore.internship.ecommerce.data.models.CategoryModel;
import com.intcore.internship.ecommerce.ui.baseClasses.BaseViewModel;
import com.intcore.internship.ecommerce.ui.commonClasses.ToastsHelper;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends BaseViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName() ;

    private MutableLiveData<CartsResponseModel> cartItemsLD ;
    MutableLiveData<CartsResponseModel> getCartItemsLD() {
        if (cartItemsLD == null)
            cartItemsLD = new MutableLiveData<>();
        return cartItemsLD;
    }

    private MutableLiveData<List<CategoryModel>> categoriesLD ;
    MutableLiveData<List<CategoryModel>> getCategoriesLD() {
        if (categoriesLD == null)
            categoriesLD = new MutableLiveData<>();
        return categoriesLD;
    }

    private MutableLiveData<ProfileResponseModel> profileResponseLD ;
    MutableLiveData<ProfileResponseModel> getProfileResponseLD() {
        if (profileResponseLD == null)
            profileResponseLD = new MutableLiveData<>();
        return profileResponseLD;
    }

    private DataManager dataManager ;

    public MainViewModel(@NonNull Application application) {
        super(application);
        dataManager = getCompositionRoot().getDataManager() ;
    }

    void getCarts() {
        if (dataManager.isUserLoggedIn()) {
            Log.d(TAG,"Starting GetCarts call ...") ;
            getCompositeDisposable().add(dataManager
                    .getCarts()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        if (response.isSuccessful()) {
                            Log.d(TAG,"GetCarts Success") ;
                            getCartItemsLD().setValue(response.body());
                        } else {
                            Log.d(TAG, "GetCarts failure: "+response.errorBody().string());
                        }
                    }, throwable -> {
                        Log.d(TAG, "GetCarts throwable: "+throwable.getMessage());
                        setToastMessagesLD(new ToastsHelper.ToastMessage(getApplication().getString(R.string.connection_error),ToastsHelper.MESSAGE_TYPE_ERROR));
                    })
            );
        }
    }

    void getCategories() {
        Log.d(TAG, "Starting GetCategories call ...");
        getCompositeDisposable().add(dataManager
                .getCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.isSuccessful()) {
                        Log.d(TAG, "GetCategories Success");
                        getCategoriesLD().setValue(response.body());
                    } else {
                        Log.d(TAG, "GetCategories failure: " + response.errorBody().string());
                    }
                }, throwable -> {
                    Log.d(TAG, "GetCategories throwable: " + throwable.getMessage());
                    setToastMessagesLD(new ToastsHelper.ToastMessage(getApplication().getString(R.string.connection_error),ToastsHelper.MESSAGE_TYPE_ERROR));
                })
        );
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

    public String getSavedLocale() {
        return dataManager.getSavedLocale();
    }
}
