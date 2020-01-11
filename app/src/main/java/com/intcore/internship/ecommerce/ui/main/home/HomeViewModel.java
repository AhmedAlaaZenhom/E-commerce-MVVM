package com.intcore.internship.ecommerce.ui.main.home;

import android.app.Application;
import android.util.Log;

import com.intcore.internship.ecommerce.R;
import com.intcore.internship.ecommerce.data.DataManager;
import com.intcore.internship.ecommerce.data.models.CategoryModel;
import com.intcore.internship.ecommerce.data.models.ProductModel;
import com.intcore.internship.ecommerce.data.remote.helperModels.home.HomeResponseModel;
import com.intcore.internship.ecommerce.ui.baseClasses.BaseViewModel;
import com.intcore.internship.ecommerce.ui.commonClasses.ToastsHelper;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class HomeViewModel extends BaseViewModel {

    public static String TAG = HomeViewModel.class.getSimpleName();

    private MutableLiveData<HomeResponseModel> homeResponseModelLD;

    MutableLiveData<HomeResponseModel> getHomeResponseModelLD() {
        if (homeResponseModelLD == null)
            homeResponseModelLD = new MutableLiveData<>();
        return homeResponseModelLD;
    }

    private DataManager dataManager;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        dataManager = getCompositionRoot().getDataManager();
    }

    void getHome() {
        getCompositeDisposable().add(dataManager
                .getHome()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe(homeResponseModel -> {
                    setSwipeRefreshLoadingLD(false);
                    sortHomeData(homeResponseModel);
                    getHomeResponseModelLD().setValue(homeResponseModel);
                }, throwable -> {
                    setSwipeRefreshLoadingLD(false);
                })
        );
    }

    private void sortHomeData(HomeResponseModel homeResponseModel) {
        for (int i = 0; i < homeResponseModel.getNewArrivalModelList().size(); i++) {
            for (int j = 0; j < homeResponseModel.getNewArrivalModelList().size() - 1; j++) {
                final ProductModel productModel1 = homeResponseModel.getNewArrivalModelList().get(j);
                final ProductModel productModel2 = homeResponseModel.getNewArrivalModelList().get(j + 1);
                final ProductModel productModel3 = homeResponseModel.getBestSellerModelList().get(j);
                final ProductModel productModel4 = homeResponseModel.getBestSellerModelList().get(j + 1);
                if (productModel1.getId() > productModel2.getId())
                    Collections.swap(homeResponseModel.getNewArrivalModelList(), j, j + 1);
                if (productModel3.getId() > productModel4.getId())
                    Collections.swap(homeResponseModel.getBestSellerModelList(), j, j + 1);
            }
        }
    }

    void toggleFavState(Integer productID) {
        if (dataManager.isUserLoggedIn()) {
            Log.d(TAG, "Starting ToggleFavState call ...");
            getCompositeDisposable().add(dataManager
                    .toggleFavouriteState(productID)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        getHome();
                        if (response.isSuccessful()) {
                            Log.d(TAG, "ToggleFavState Success");
                            String responseMessage = response.body().getMessage() ;
                            if(responseMessage.contains("removed"))
                                setToastMessagesLD(new ToastsHelper.ToastMessage(getApplication().getString(R.string.removed_from_fav),ToastsHelper.MESSAGE_TYPE_SUCCESS));
                            else
                                setToastMessagesLD(new ToastsHelper.ToastMessage(getApplication().getString(R.string.added_to_fav),ToastsHelper.MESSAGE_TYPE_SUCCESS));
                        } else {
                            Log.d(TAG, "ToggleFavState failure, ErrorBody: " + response.errorBody().toString());
                            setToastMessagesLD(new ToastsHelper.ToastMessage(getApplication().getString(R.string.unknown_error),ToastsHelper.MESSAGE_TYPE_WARNING));
                        }
                    }, throwable -> {
                        getHome();
                        Log.d(TAG, "ToggleFavState throwable, ThrowableMessage: " + throwable.getMessage());
                        setToastMessagesLD(new ToastsHelper.ToastMessage(getApplication().getString(R.string.connection_error),ToastsHelper.MESSAGE_TYPE_ERROR));
                    }));
        }
    }

    public String getSavedLocale() {
        return dataManager.getSavedLocale();
    }
}
