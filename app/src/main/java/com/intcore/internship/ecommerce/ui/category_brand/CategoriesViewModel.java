package com.intcore.internship.ecommerce.ui.category_brand;

import android.app.Application;
import android.util.Log;

import com.intcore.internship.ecommerce.R;
import com.intcore.internship.ecommerce.data.DataManager;
import com.intcore.internship.ecommerce.data.models.CategoryModel;
import com.intcore.internship.ecommerce.ui.baseClasses.BaseViewModel;
import com.intcore.internship.ecommerce.ui.commonClasses.ToastsHelper;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CategoriesViewModel extends BaseViewModel {

    private static final String TAG = CategoriesViewModel.class.getSimpleName();

    private MutableLiveData<List<CategoryModel>> categoriesListLD;

    MutableLiveData<List<CategoryModel>> getCategoriesListLD() {
        return categoriesListLD;
    }

    private DataManager dataManager;

    public CategoriesViewModel(@NonNull Application application) {
        super(application);
        dataManager = getCompositionRoot().getDataManager();
        categoriesListLD = new MutableLiveData<>();
    }

    void getCategories() {
        Log.d(TAG, "Starting GetCategories call ...");
        setProgressLoadingLD(true);
        getCompositeDisposable().add(dataManager
                .getCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    setProgressLoadingLD(false);
                    if (response.isSuccessful()) {
                        Log.d(TAG, "GetCategories Success");
                        getCategoriesListLD().setValue(response.body());
                    } else {
                        Log.d(TAG, "GetCategories failure: " + response.errorBody().string());
                    }
                }, throwable -> {
                    setProgressLoadingLD(false);
                    Log.d(TAG, "GetCategories throwable: " + throwable.getMessage());
                    setToastMessagesLD(new ToastsHelper.ToastMessage(getApplication().getString(R.string.connection_error),ToastsHelper.MESSAGE_TYPE_ERROR));
                })
        );
    }

    public String getSavedLocale() {
        return dataManager.getSavedLocale();
    }
}
