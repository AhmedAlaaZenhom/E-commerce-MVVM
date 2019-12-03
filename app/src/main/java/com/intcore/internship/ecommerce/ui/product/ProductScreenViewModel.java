package com.intcore.internship.ecommerce.ui.product;

import android.app.Application;
import android.util.Log;

import com.intcore.internship.ecommerce.R;
import com.intcore.internship.ecommerce.data.DataManager;
import com.intcore.internship.ecommerce.data.models.CartItemModel;
import com.intcore.internship.ecommerce.data.models.ProductModel;
import com.intcore.internship.ecommerce.ui.baseClasses.BaseViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProductScreenViewModel extends BaseViewModel {

    private static final String TAG = ProductScreenViewModel.class.getSimpleName() ;

    private MutableLiveData<ProductModel> productModelLD ;
    MutableLiveData<ProductModel> getProductModelLD() {
        if(productModelLD==null)
            productModelLD = new MutableLiveData<>() ;
        return productModelLD;
    }

    private MutableLiveData<List<CartItemModel>> cartsItemsLD ;
    MutableLiveData<List<CartItemModel>> getCartItemsLD() {
        if(cartsItemsLD==null)
            cartsItemsLD = new MutableLiveData<>() ;
        return cartsItemsLD;
    }

    private DataManager dataManager ;

    public ProductScreenViewModel(@NonNull Application application) {
        super(application);
        dataManager = getCompositionRoot().getDataManager() ;
    }

    void getProduct(int productID) {
        Log.d(TAG, "Starting GetProduct call ...");
        getCompositeDisposable().add(dataManager
                .getProduct(productID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(productModelResponse -> {
                    if (productModelResponse.isSuccessful()) {
                        Log.d(TAG, "GetProduct call success ...");
                        getProductModelLD().setValue(productModelResponse.body());
                    } else {
                        Log.d(TAG, "GetProduct failure: " + productModelResponse.errorBody().string());
                        setToastMessagesLD(getApplication().getString(R.string.unknown_error));
                    }
                }, throwable -> {
                    Log.d(TAG, "GetProduct throwable: " + throwable.getMessage());
                    setToastMessagesLD(getApplication().getString(R.string.connection_error));
                }));
    }

    void addToCart(Integer productID, Integer quantity, Integer sizeID, Integer colorID) {
        Log.d(TAG, "Starting AddToCart call ...");
        setProgressLoadingLD(true);
        getCompositeDisposable().add(dataManager
                .addToCart(productID, quantity, sizeID, colorID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    setProgressLoadingLD(false);
                    if (response.isSuccessful()) {
                        Log.d(TAG, "AddToCart call success ...");
                        getCartItemsLD().setValue(response.body().getCartItemModelList());
                    } else {
                        Log.d(TAG, "AddToCart failure: " + response.errorBody().string());
                        setToastMessagesLD(getApplication().getString(R.string.unknown_error));
                    }
                }, throwable -> {
                    Log.d(TAG, "AddToCart throwable: " + throwable.getMessage());
                    setProgressLoadingLD(false);
                    setToastMessagesLD(getApplication().getString(R.string.connection_error));
                }));
    }

    void getCarts() {
        if (dataManager.isUserLoggedIn()) {
            Log.d(TAG, "Starting GetCarts call ...");
            getCompositeDisposable().add(dataManager
                    .getCarts()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "GetCarts Success");
                            getCartItemsLD().setValue(response.body().getCartItemModelList());
                        } else {
                            Log.d(TAG, "GetCarts failure: " + response.errorBody().string());
                        }
                    }, throwable -> {
                        Log.d(TAG, "GetCarts throwable: " + throwable.getMessage());
                        setToastMessagesLD(getApplication().getString(R.string.connection_error));
                    })
            );
        }
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
                            getProduct(productID);
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
