package com.intcore.internship.ecommerce.ui.main.deals;

import android.app.Application;
import android.util.Log;

import com.intcore.internship.ecommerce.R;
import com.intcore.internship.ecommerce.data.DataManager;
import com.intcore.internship.ecommerce.data.remote.helperModels.deals.DealsPageResponseModel;
import com.intcore.internship.ecommerce.ui.baseClasses.BaseViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DealsViewModel extends BaseViewModel {

    public static String TAG = DealsViewModel.class.getSimpleName() ;

    private MutableLiveData<DealsPageResponseModel> dealsPageResponseModelLD ;
    MutableLiveData<DealsPageResponseModel> getDealsPageResponseModelLD() {
        if(dealsPageResponseModelLD==null)
            dealsPageResponseModelLD = new MutableLiveData<>() ;
        return dealsPageResponseModelLD;
    }

    private DataManager dataManager ;

    public DealsViewModel(@NonNull Application application) {
        super(application);
        dataManager = getCompositionRoot().getDataManager() ;
    }

    public void getDealsPage(){
        Log.d(TAG, "Starting GetDealsPage call ...");
        getCompositeDisposable().add(dataManager
                .getDealsPage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dealsPageResponseModelResponse -> {
                    if (dealsPageResponseModelResponse.isSuccessful()) {
                        Log.d(TAG, "GetDealsPage Success");
                        getDealsPageResponseModelLD().setValue(dealsPageResponseModelResponse.body());
                    } else {
                        Log.d(TAG, "GetDealsPage failure, ErrorBody: " + dealsPageResponseModelResponse.errorBody().toString());
                        setToastMessagesLD(getApplication().getString(R.string.unknown_error));
                    }
                }, throwable -> {
                    Log.d(TAG, "GetDealsPage throwable, ThrowableMessage: " + throwable.getMessage());
                    setToastMessagesLD(getApplication().getString(R.string.connection_error));
                }));
    }

}
