package com.intcore.internship.ecommerce.ui.orders;

import android.app.Application;
import android.util.Log;

import com.intcore.internship.ecommerce.R;
import com.intcore.internship.ecommerce.data.DataManager;
import com.intcore.internship.ecommerce.data.models.OrderModel;
import com.intcore.internship.ecommerce.ui.baseClasses.BaseViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class OrdersViewModel extends BaseViewModel {

    private static final String TAG = OrdersViewModel.class.getSimpleName() ;

    private MutableLiveData<List<OrderModel>> orderModelListLD ;
    MutableLiveData<List<OrderModel>> getOrderModelListLD() {
        orderModelListLD = new MutableLiveData<>() ;
        return orderModelListLD;
    }

    private DataManager dataManager ;

    public OrdersViewModel(@NonNull Application application) {
        super(application);
        dataManager = getCompositionRoot().getDataManager() ;
    }

    void getOrders() {
        Log.d(TAG, "Starting GetOrders call ...");
        getCompositeDisposable().add(dataManager
                .getOrders()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.isSuccessful()) {
                        Log.d(TAG, "GetOrders Success");
                        getOrderModelListLD().setValue(response.body());
                    } else {
                        Log.d(TAG, "GetOrders failure, ErrorBody: " + response.errorBody().toString());
                        setToastMessagesLD(getApplication().getString(R.string.unknown_error));
                    }
                }, throwable -> {
                    Log.d(TAG, "GetOrders throwable, ThrowableMessage: " + throwable.getMessage());
                    setToastMessagesLD(getApplication().getString(R.string.connection_error));
                }));
    }
}
