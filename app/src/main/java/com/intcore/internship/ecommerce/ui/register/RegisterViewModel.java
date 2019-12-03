package com.intcore.internship.ecommerce.ui.register;

import android.app.Application;
import android.util.Log;

import com.google.gson.Gson;
import com.intcore.internship.ecommerce.R;
import com.intcore.internship.ecommerce.data.DataManager;
import com.intcore.internship.ecommerce.data.models.UserModel;
import com.intcore.internship.ecommerce.data.remote.helperModels.register.RegisterErrorModel;
import com.intcore.internship.ecommerce.data.remote.helperModels.register.RegisterResponseModel;
import com.intcore.internship.ecommerce.ui.baseClasses.BaseViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RegisterViewModel extends BaseViewModel {

    private static String TAG = RegisterViewModel.class.getSimpleName() ;

    private MutableLiveData<UserModel> registerResponseLD ;
    public MutableLiveData<UserModel> getRegisterResponseLD() {
        if(registerResponseLD==null)
            registerResponseLD = new MutableLiveData<>() ;
        return registerResponseLD;
    }

    private DataManager dataManager ;

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        dataManager = getCompositionRoot().getDataManager() ;
    }

    void register(String name, String email, String phone ,  String password) {
        Log.d(TAG, "Register call started");
        setProgressLoadingLD(true);
        getCompositeDisposable().add(dataManager
                .register(name, email, phone, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    setProgressLoadingLD(false);
                    if (response.isSuccessful() && response.body() != null) {
                        final UserModel userModel = response.body().getUserModel();
                        dataManager.setUserLoggedIn();
                        dataManager.saveUserData(
                                String.valueOf(userModel.getId()),
                                userModel.getName(),
                                userModel.getApiToken());
                        getRegisterResponseLD().setValue(userModel);
                    } else if (!response.isSuccessful() && response.errorBody() != null) {
                        RegisterErrorModel errorModel = new Gson()
                                .fromJson(response.errorBody().string(), RegisterResponseModel.class)
                                .getErrorModelList()
                                .get(0);
                        setToastMessagesLD(errorModel.getMessage());
                    } else {
                        setToastMessagesLD(getApplication().getString(R.string.unknown_error));
                    }
                }, throwable -> {
                    Log.d(TAG, "Register error: " + throwable.getMessage());
                    setProgressLoadingLD(false);
                    setToastMessagesLD(getApplication().getString(R.string.connection_error));
                }));
    }


    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
