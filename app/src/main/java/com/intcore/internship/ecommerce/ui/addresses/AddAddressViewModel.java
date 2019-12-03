package com.intcore.internship.ecommerce.ui.addresses;

import android.app.Application;
import android.util.Log;

import com.intcore.internship.ecommerce.R;
import com.intcore.internship.ecommerce.data.DataManager;
import com.intcore.internship.ecommerce.data.models.AddressModel;
import com.intcore.internship.ecommerce.ui.baseClasses.BaseViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AddAddressViewModel extends BaseViewModel {

    public static final String TAG = AddAddressViewModel.class.getSimpleName() ;

    private MutableLiveData<AddressModel> addAddressResponseModelLD;
    MutableLiveData<AddressModel> getAddAddressResponseModelLD() {
        if(addAddressResponseModelLD ==null)
            addAddressResponseModelLD = new MutableLiveData<>() ;
        return addAddressResponseModelLD;
    }

    private DataManager dataManager ;

    public AddAddressViewModel(@NonNull Application application) {
        super(application);
        dataManager = getCompositionRoot().getDataManager() ;
    }

    void addAddress(
            String city,
            String street,
            String building,
            String floor,
            String apartment,
            String landmark,
            String phoneNumber,
            String notes){
        Log.d(TAG, "Starting AddAddresses call ...");
        getCompositeDisposable().add(dataManager
                .addAddress(city, street, building, floor, apartment, landmark, phoneNumber, notes)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.isSuccessful()) {
                        Log.d(TAG, "AddAddresses call success ...");
                        setToastMessagesLD(response.body().getMessage());
                        getAddAddressResponseModelLD().setValue(response.body().getAddressModel());
                    } else {
                        Log.d(TAG, "AddAddresses failure: " + response.errorBody().string());
                        setToastMessagesLD(getApplication().getString(R.string.unknown_error));
                    }
                }, throwable -> {
                    Log.d(TAG, "AddAddresses throwable: " + throwable.getMessage());
                    setToastMessagesLD(getApplication().getString(R.string.connection_error));
                }));
    }
}
