package com.intcore.internship.ecommerce.ui.category_brand;

import android.app.Application;
import android.util.Log;

import com.intcore.internship.ecommerce.R;
import com.intcore.internship.ecommerce.data.DataManager;
import com.intcore.internship.ecommerce.data.models.ProductModel;
import com.intcore.internship.ecommerce.data.remote.helperModels.filter.FilterDataResponseModel;
import com.intcore.internship.ecommerce.ui.baseClasses.BaseViewModel;
import com.intcore.internship.ecommerce.ui.commonClasses.ToastsHelper;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CategoryBrandViewModel extends BaseViewModel {

    private static final String TAG = CategoryBrandViewModel.class.getSimpleName();

    private MutableLiveData<List<ProductModel>> productModelListLD;

    MutableLiveData<List<ProductModel>> getProductModelListLD() {
        return productModelListLD;
    }

    private MutableLiveData<FilterDataResponseModel> filterDataResponseModelLD ;

    MutableLiveData<FilterDataResponseModel> getFilterDataResponseModelLD() {
        return filterDataResponseModelLD;
    }

    private DataManager dataManager;

    private @Nullable
    String lastKeyword;
    private @Nullable
    Integer lastCategoryID;
    private @Nullable
    Integer lastSubCategoryID;
    private @Nullable
    Integer lastBrandID;
    private @Nullable
    Integer lastMaxPrice;
    private @Nullable
    Integer lastMinPrice;
    private @Nullable
    Integer lastSortBy;

    public CategoryBrandViewModel(@NonNull Application application) {
        super(application);
        dataManager = getCompositionRoot().getDataManager();
        productModelListLD = new MutableLiveData<>();
        filterDataResponseModelLD = new MutableLiveData<>();
    }

    void getProducts(@Nullable String keyword,
                     @Nullable Integer categoryID,
                     @Nullable Integer subCategoryID,
                     @Nullable Integer brandID,
                     @Nullable Integer maxPrice,
                     @Nullable Integer minPrice,
                     @Nullable Integer sortBy) {
        this.lastKeyword = keyword;
        this.lastCategoryID = categoryID;
        this.lastSubCategoryID = subCategoryID;
        this.lastBrandID = brandID;
        this.lastMaxPrice = maxPrice;
        this.lastMinPrice = minPrice;
        this.lastSortBy = sortBy;
        Log.d(TAG, "Starting GetProducts call ...");
        getCompositeDisposable().add(dataManager
                .getProducts(keyword, categoryID, subCategoryID, brandID, maxPrice, minPrice, sortBy)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(productModelResponse -> {
                    setSwipeRefreshLoadingLD(false);
                    if (productModelResponse.isSuccessful() && productModelResponse.body() != null) {
                        Log.d(TAG, "GetProducts call success ...");
                        getProductModelListLD().setValue(productModelResponse.body().getProductModelList());
                    } else if (productModelResponse.errorBody() != null) {
                        Log.d(TAG, "GetProducts failure: " + productModelResponse.errorBody().string());
                        setToastMessagesLD(new ToastsHelper.ToastMessage(getApplication().getString(R.string.unknown_error), ToastsHelper.MESSAGE_TYPE_WARNING));
                    } else {
                        Log.d(TAG, "GetProducts failure: Unknown");
                        setToastMessagesLD(new ToastsHelper.ToastMessage(getApplication().getString(R.string.unknown_error), ToastsHelper.MESSAGE_TYPE_WARNING));
                    }
                }, throwable -> {
                    Log.d(TAG, "GetProducts throwable: " + throwable.getMessage());
                    setSwipeRefreshLoadingLD(false);
                    setToastMessagesLD(new ToastsHelper.ToastMessage(getApplication().getString(R.string.connection_error), ToastsHelper.MESSAGE_TYPE_ERROR));
                }));
    }

    void toggleFavState(Integer productID) {
        if (dataManager.isUserLoggedIn()) {
            Log.d(TAG, "Starting ToggleFavState call ...");
            getCompositeDisposable().add(dataManager
                    .toggleFavouriteState(productID)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        getProducts(lastKeyword, lastCategoryID, lastSubCategoryID, lastBrandID, lastMaxPrice, lastMinPrice, lastSortBy);
                        if (response.isSuccessful()) {
                            Log.d(TAG, "ToggleFavState Success");
                            String responseMessage = response.body().getMessage();
                            if (responseMessage.contains("removed"))
                                setToastMessagesLD(new ToastsHelper.ToastMessage(getApplication().getString(R.string.removed_from_fav), ToastsHelper.MESSAGE_TYPE_SUCCESS));
                            else
                                setToastMessagesLD(new ToastsHelper.ToastMessage(getApplication().getString(R.string.added_to_fav), ToastsHelper.MESSAGE_TYPE_SUCCESS));
                        } else {
                            Log.d(TAG, "ToggleFavState failure, ErrorBody: " + response.errorBody().toString());
                            setToastMessagesLD(new ToastsHelper.ToastMessage(getApplication().getString(R.string.unknown_error), ToastsHelper.MESSAGE_TYPE_WARNING));
                        }
                    }, throwable -> {
                        Log.d(TAG, "ToggleFavState throwable, ThrowableMessage: " + throwable.getMessage());
                        setToastMessagesLD(new ToastsHelper.ToastMessage(getApplication().getString(R.string.connection_error), ToastsHelper.MESSAGE_TYPE_ERROR));
                    }));
        }
    }

    void getFilterData(Integer categoryID) {
        if (dataManager.isUserLoggedIn()) {
            Log.d(TAG, "Starting ToggleFavState call ...");
            getCompositeDisposable().add(dataManager
                    .getFilterData(categoryID)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "GetFilterData Success");
                            getFilterDataResponseModelLD().setValue(response.body());
                        } else {
                            Log.d(TAG, "GetFilterData failure, ErrorBody: " + response.errorBody().toString());
                        }
                    }, throwable -> {
                        Log.d(TAG, "GetFilterData throwable, ThrowableMessage: " + throwable.getMessage());
                        setToastMessagesLD(new ToastsHelper.ToastMessage(getApplication().getString(R.string.connection_error), ToastsHelper.MESSAGE_TYPE_ERROR));
                    }));
        }
    }

    public String getSavedLocale() {
        return dataManager.getSavedLocale();
    }
}
