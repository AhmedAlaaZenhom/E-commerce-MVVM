package com.intcore.internship.ecommerce.ui.checkout;

import android.app.Application;
import android.util.Log;

import com.intcore.internship.ecommerce.R;
import com.intcore.internship.ecommerce.data.DataManager;
import com.intcore.internship.ecommerce.data.remote.helperModels.cart.CartsResponseModel;
import com.intcore.internship.ecommerce.data.remote.helperModels.order.OrderResponseModel;
import com.intcore.internship.ecommerce.data.models.AddressModel;
import com.intcore.internship.ecommerce.ui.baseClasses.BaseViewModel;
import com.intcore.internship.ecommerce.ui.commonClasses.ToastsHelper;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CheckoutViewModel extends BaseViewModel {

    private static final String TAG = CheckoutViewModel.class.getSimpleName() ;

    private MutableLiveData<List<AddressModel>> addressesLD ;
    MutableLiveData<List<AddressModel>> getAddressesLD() {
        if (addressesLD == null)
            addressesLD = new MutableLiveData<>();
        return addressesLD;
    }

    private MutableLiveData<CartsResponseModel> cartResponseLD ;
    MutableLiveData<CartsResponseModel> getCartResponseLD() {
        if(cartResponseLD==null)
            cartResponseLD = new MutableLiveData<>() ;
        return cartResponseLD;
    }

    private MutableLiveData<OrderResponseModel> orderResponseModelLD ;
    MutableLiveData<OrderResponseModel> getOrderResponseModelLD() {
        if(orderResponseModelLD==null)
            orderResponseModelLD = new MutableLiveData<>() ;
        return orderResponseModelLD;
    }

    private DataManager dataManager ;

    public CheckoutViewModel(@NonNull Application application) {
        super(application);
        dataManager = getCompositionRoot().getDataManager() ;
    }

    void getAddresses() {
        Log.d(TAG, "Starting GetAddresses call ...");
        getCompositeDisposable().add(dataManager
                .getAddresses()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.isSuccessful()) {
                        Log.d(TAG, "GetAddresses call success ...");
                        getAddressesLD().setValue(response.body());
                    } else {
                        Log.d(TAG, "GetAddresses failure: " + response.errorBody().string());
                        setToastMessagesLD(new ToastsHelper.ToastMessage(getApplication().getString(R.string.unknown_error),ToastsHelper.MESSAGE_TYPE_WARNING));
                    }
                }, throwable -> {
                    Log.d(TAG, "GetAddresses throwable: " + throwable.getMessage());
                    setToastMessagesLD(new ToastsHelper.ToastMessage(getApplication().getString(R.string.connection_error),ToastsHelper.MESSAGE_TYPE_ERROR));
                }));
    }

    void getCarts() {
        Log.d(TAG, "Starting GetCarts call ...");
        setProgressLoadingLD(true);
        getCompositeDisposable().add(dataManager
                .getCarts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    setProgressLoadingLD(false);
                    if (response.isSuccessful()) {
                        Log.d(TAG, "GetCarts Success");
                        getCartResponseLD().setValue(response.body());
                    } else {
                        Log.d(TAG, "GetCarts failure: " + response.errorBody().string());
                    }
                }, throwable -> {
                    Log.d(TAG, "GetCarts throwable: " + throwable.getMessage());
                    setProgressLoadingLD(false);
                    setToastMessagesLD(new ToastsHelper.ToastMessage(getApplication().getString(R.string.connection_error),ToastsHelper.MESSAGE_TYPE_ERROR));
                })
        );
    }

    void createOrder(Integer addressID){
        Log.d(TAG, "Starting CreateOrder call ...");
        setProgressLoadingLD(true);
        getCompositeDisposable().add(dataManager
                .createOrder(addressID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    setProgressLoadingLD(false);
                    if (response.isSuccessful()) {
                        Log.d(TAG, "CreateOrder Success");
                        getOrderResponseModelLD().setValue(response.body());
                    } else {
                        Log.d(TAG, "CreateOrder failure: " + response.errorBody().string());
                    }
                }, throwable -> {
                    Log.d(TAG, "CreateOrder throwable: " + throwable.getMessage());
                    setProgressLoadingLD(false);
                    setToastMessagesLD(new ToastsHelper.ToastMessage(getApplication().getString(R.string.connection_error),ToastsHelper.MESSAGE_TYPE_ERROR));
                })
        );
    }

    public String getSavedLocale() {
        return dataManager.getSavedLocale();
    }
}
