package com.intcore.internship.ecommerce.ui.main.cart;

import android.app.Application;
import android.util.Log;

import com.intcore.internship.ecommerce.R;
import com.intcore.internship.ecommerce.data.DataManager;
import com.intcore.internship.ecommerce.data.remote.helperModels.cart.CartsResponseModel;
import com.intcore.internship.ecommerce.data.models.AddressModel;
import com.intcore.internship.ecommerce.ui.baseClasses.BaseViewModel;
import com.intcore.internship.ecommerce.ui.commonClasses.ToastsHelper;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CartViewModel extends BaseViewModel {

    private static final String TAG = CartViewModel.class.getSimpleName() ;

    private MutableLiveData<CartsResponseModel> cartItemsLD ;
    MutableLiveData<CartsResponseModel> getCartItemsLD() {
        if(cartItemsLD==null)
            cartItemsLD = new MutableLiveData<>() ;
        return cartItemsLD;
    }

    private MutableLiveData<List<AddressModel>> addressesLD ;
    MutableLiveData<List<AddressModel>> getAddressesLD() {
        if(addressesLD==null)
            addressesLD = new MutableLiveData<>() ;
        return addressesLD;
    }

    private DataManager dataManager ;

    public CartViewModel(@NonNull Application application) {
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
                        setSwipeRefreshLoadingLD(false);
                        if (response.isSuccessful()) {
                            Log.d(TAG,"GetCarts Success") ;
                            getCartItemsLD().setValue(response.body());
                        } else {
                            Log.d(TAG, "GetCarts failure: "+response.errorBody().string());
                        }
                    }, throwable -> {
                        setSwipeRefreshLoadingLD(false);
                        Log.d(TAG, "GetCarts throwable: "+throwable.getMessage());
                        setToastMessagesLD(new ToastsHelper.ToastMessage(getApplication().getString(R.string.connection_error),ToastsHelper.MESSAGE_TYPE_ERROR));
                    })
            );
        }
    }

    void incrementCartItemQuantity(Integer cartItemID) {
        Log.d(TAG, "Starting UpdateCartItemQuantity call ...");
        setProgressLoadingLD(true);
        getCompositeDisposable().add(dataManager
                .incrementCartItemQuantity(cartItemID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    setProgressLoadingLD(false);
                    if (response.isSuccessful()) {
                        Log.d(TAG, "UpdateCartItemQuantity Success");
                        getCartItemsLD().setValue(response.body());
                    } else {
                        Log.d(TAG, "UpdateCartItemQuantity failure: " + response.errorBody().string());
                    }
                }, throwable -> {
                    setProgressLoadingLD(false);
                    Log.d(TAG, "UpdateCartItemQuantity throwable: " + throwable.getMessage());
                    setToastMessagesLD(new ToastsHelper.ToastMessage(getApplication().getString(R.string.connection_error),ToastsHelper.MESSAGE_TYPE_ERROR));
                })
        );
    }

    void decrementCartItemQuantity(Integer cartItemID) {
        Log.d(TAG, "Starting UpdateCartItemQuantity call ...");
        setProgressLoadingLD(true);
        getCompositeDisposable().add(dataManager
                .decrementCartItemQuantity(cartItemID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    setProgressLoadingLD(false);
                    if (response.isSuccessful()) {
                        Log.d(TAG, "UpdateCartItemQuantity Success");
                        getCartItemsLD().setValue(response.body());
                    } else {
                        Log.d(TAG, "UpdateCartItemQuantity failure: " + response.errorBody().string());
                    }
                }, throwable -> {
                    setProgressLoadingLD(false);
                    Log.d(TAG, "UpdateCartItemQuantity throwable: " + throwable.getMessage());
                    setToastMessagesLD(new ToastsHelper.ToastMessage(getApplication().getString(R.string.connection_error),ToastsHelper.MESSAGE_TYPE_ERROR));
                })
        );
    }

    void removeCartItem(Integer cartItemID){
        Log.d(TAG, "Starting RemoveCartItemQuantity call ...");
        setProgressLoadingLD(true);
        getCompositeDisposable().add(dataManager
                .removeCartItem(cartItemID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    setProgressLoadingLD(false);
                    if (response.isSuccessful()) {
                        Log.d(TAG, "RemoveCartItemQuantity Success");
                        getCartItemsLD().setValue(response.body());
                    } else {
                        Log.d(TAG, "RemoveCartItemQuantity failure: " + response.errorBody().string());
                    }
                }, throwable -> {
                    setProgressLoadingLD(false);
                    Log.d(TAG, "RemoveCartItemQuantity throwable: " + throwable.getMessage());
                    setToastMessagesLD(new ToastsHelper.ToastMessage(getApplication().getString(R.string.connection_error),ToastsHelper.MESSAGE_TYPE_ERROR));
                })
        );
    }

    void getAddresses() {
        Log.d(TAG, "Starting GetAddresses call ...");
        setProgressLoadingLD(true);
        getCompositeDisposable().add(dataManager
                .getAddresses()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    setProgressLoadingLD(false);
                    if (response.isSuccessful()) {
                        Log.d(TAG, "GetAddresses call success ...");
                        getAddressesLD().setValue(response.body());
                    } else {
                        Log.d(TAG, "GetAddresses failure: " + response.errorBody().string());
                        setToastMessagesLD(new ToastsHelper.ToastMessage(getApplication().getString(R.string.connection_error),ToastsHelper.MESSAGE_TYPE_ERROR));
                    }
                }, throwable -> {
                    setProgressLoadingLD(false);
                    Log.d(TAG, "GetAddresses throwable: " + throwable.getMessage());
                    setToastMessagesLD(new ToastsHelper.ToastMessage(getApplication().getString(R.string.connection_error),ToastsHelper.MESSAGE_TYPE_ERROR));
                }));
    }

    public String getSavedLocale() {
        return dataManager.getSavedLocale();
    }
}
