package com.intcore.internship.ecommerce.ui.main.wishlist;

import android.app.Application;
import android.util.Log;

import com.intcore.internship.ecommerce.R;
import com.intcore.internship.ecommerce.data.DataManager;
import com.intcore.internship.ecommerce.data.models.ProductModel;
import com.intcore.internship.ecommerce.ui.baseClasses.BaseViewModel;
import com.intcore.internship.ecommerce.ui.commonClasses.ToastsHelper;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WishListViewModel extends BaseViewModel {

    private static final String TAG = WishListViewModel.class.getSimpleName();

    private MutableLiveData<List<ProductModel>> productModelListLD ;
    MutableLiveData<List<ProductModel>> getProductModelListLD() {
        if (productModelListLD == null)
            productModelListLD = new MutableLiveData<>();
        return productModelListLD;
    }

    private DataManager dataManager ;

    public WishListViewModel(@NonNull Application application) {
        super(application);
        dataManager = getCompositionRoot().getDataManager() ;
    }

    void getWishList(){
        if(dataManager.isUserLoggedIn()){
            Log.d(TAG, "Starting GetWishList call ...");
            getCompositeDisposable().add(dataManager
                    .getWishList()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "GetWishList Success");
                            getProductModelListLD().setValue(response.body().getProductModelList());
                        } else {
                            Log.d(TAG, "GetWishList failure, ErrorBody: " + response.errorBody().toString());
                            setToastMessagesLD(new ToastsHelper.ToastMessage(getApplication().getString(R.string.unknown_error),ToastsHelper.MESSAGE_TYPE_WARNING));
                        }
                    }, throwable -> {
                        Log.d(TAG, "GetWishList throwable, ThrowableMessage: " + throwable.getMessage());
                        setToastMessagesLD(new ToastsHelper.ToastMessage(getApplication().getString(R.string.connection_error),ToastsHelper.MESSAGE_TYPE_ERROR));
                    }));
        }
    }

    void toggleFavState(Integer productID) {
        if (dataManager.isUserLoggedIn()) {
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
                            String responseMessage = response.body().getMessage() ;
                            if(responseMessage.contains("removed"))
                                setToastMessagesLD(new ToastsHelper.ToastMessage(getApplication().getString(R.string.removed_from_fav),ToastsHelper.MESSAGE_TYPE_SUCCESS));
                            else
                                setToastMessagesLD(new ToastsHelper.ToastMessage(getApplication().getString(R.string.added_to_fav),ToastsHelper.MESSAGE_TYPE_SUCCESS));
                            // Get wish list again to refresh products
                            getWishList();
                        } else {
                            Log.d(TAG, "ToggleFavState failure, ErrorBody: " + response.errorBody().toString());
                            setProgressLoadingLD(false);
                            setToastMessagesLD(new ToastsHelper.ToastMessage(getApplication().getString(R.string.unknown_error),ToastsHelper.MESSAGE_TYPE_WARNING));
                        }
                    }, throwable -> {
                        Log.d(TAG, "ToggleFavState throwable, ThrowableMessage: " + throwable.getMessage());
                        setToastMessagesLD(new ToastsHelper.ToastMessage(getApplication().getString(R.string.connection_error),ToastsHelper.MESSAGE_TYPE_ERROR));
                    }));
        }
    }

    public String getSavedLocale() {
        return dataManager.getSavedLocale();
    }
}
