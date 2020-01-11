package com.intcore.internship.ecommerce.ui.addresses;

import android.app.Application;
import android.util.Log;

import com.intcore.internship.ecommerce.R;
import com.intcore.internship.ecommerce.data.DataManager;
import com.intcore.internship.ecommerce.data.models.AddressModel;
import com.intcore.internship.ecommerce.ui.baseClasses.BaseViewModel;
import com.intcore.internship.ecommerce.ui.commonClasses.ToastsHelper;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AddressesViewModel extends BaseViewModel {

    private static final String TAG = AddressesViewModel.class.getSimpleName() ;

    private MutableLiveData<List<AddressModel>> addressesLD ;
    public MutableLiveData<List<AddressModel>> getAddressesLD() {
        if (addressesLD == null)
            addressesLD = new MutableLiveData<>();
        return addressesLD;
    }

    private DataManager dataManager ;

    public AddressesViewModel(@NonNull Application application) {
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
                    setSwipeRefreshLoadingLD(false);
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
}
