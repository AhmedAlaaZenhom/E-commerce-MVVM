package com.intcore.internship.ecommerce.ui.main.home;

import android.app.Application;
import android.util.Log;

import com.intcore.internship.ecommerce.R;
import com.intcore.internship.ecommerce.data.DataManager;
import com.intcore.internship.ecommerce.data.models.CategoryModel;
import com.intcore.internship.ecommerce.data.models.ProductModel;
import com.intcore.internship.ecommerce.data.remote.helperModels.home.HomeResponseModel;
import com.intcore.internship.ecommerce.ui.baseClasses.BaseViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class HomeViewModel extends BaseViewModel {

    public static String TAG = HomeViewModel.class.getSimpleName() ;

    private MutableLiveData<HomeResponseModel> homeResponseModelLD ;
    MutableLiveData<HomeResponseModel> getHomeResponseModelLD() {
        if(homeResponseModelLD==null)
            homeResponseModelLD = new MutableLiveData<>();
        return homeResponseModelLD;
    }

    private DataManager dataManager ;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        dataManager = getCompositionRoot().getDataManager() ;
    }

    void getHome() {
        getCompositeDisposable().add(dataManager
                .getHome()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe(homeResponseModel -> getHomeResponseModelLD().setValue(homeResponseModel), throwable -> {})
        );
    }

    void toggleFavState(Integer productID){
        if(dataManager.isUserLoggedIn()){
            Log.d(TAG, "Starting ToggleFavState call ...");
            setProgressLoadingLD(true);
            getCompositeDisposable().add(dataManager
                    .toggleFavouriteState(productID)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "ToggleFavState Success");
                            setProgressLoadingLD(false);
                            setToastMessagesLD(response.body().getMessage());
                            // Get home again to refresh products
                            getHome();
                        } else {
                            Log.d(TAG, "ToggleFavState failure, ErrorBody: " + response.errorBody().toString());
                            setProgressLoadingLD(false);
                            setToastMessagesLD(getApplication().getString(R.string.unknown_error));
                        }
                    }, throwable -> {
                        Log.d(TAG, "ToggleFavState throwable, ThrowableMessage: " + throwable.getMessage());
                        setToastMessagesLD(getApplication().getString(R.string.connection_error));
                    }));
        }
    }
}
