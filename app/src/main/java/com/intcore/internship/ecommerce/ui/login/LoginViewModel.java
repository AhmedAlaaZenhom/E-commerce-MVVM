package com.intcore.internship.ecommerce.ui.login;

import android.app.Application;
import android.util.Log;

import com.google.gson.Gson;
import com.intcore.internship.ecommerce.R;
import com.intcore.internship.ecommerce.data.DataManager;
import com.intcore.internship.ecommerce.data.remote.helperModels.login.LoginErrorModel;
import com.intcore.internship.ecommerce.data.remote.helperModels.login.LoginResponseModel;
import com.intcore.internship.ecommerce.data.models.UserModel;
import com.intcore.internship.ecommerce.ui.baseClasses.BaseViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginViewModel extends BaseViewModel {

    private static String TAG = LoginViewModel.class.getSimpleName() ;

    private MutableLiveData<UserModel> loginResponseLD ;
    public MutableLiveData<UserModel> getLoginResponseLD() {
        if(loginResponseLD==null)
            loginResponseLD = new MutableLiveData<>() ;
        return loginResponseLD;
    }

    private DataManager dataManager ;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        dataManager = getCompositionRoot().getDataManager() ;
    }

    void loginUsingEmailAndPassword(String email, String password) {
        Log.d(TAG, "Login call started");
        setProgressLoadingLD(true);
        getCompositeDisposable().add(dataManager
                .loginUsingEmailAndPassword(email, password)
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
                        getLoginResponseLD().setValue(userModel);
                    } else if (!response.isSuccessful() && response.errorBody() != null) {
                        LoginErrorModel errorModel = new Gson()
                                .fromJson(response.errorBody().string(), LoginResponseModel.class)
                                .getErrorModelList()
                                .get(0);
                        setToastMessagesLD(errorModel.getMessage());
                    } else {
                        setToastMessagesLD(getApplication().getString(R.string.unknown_error));
                    }
                }, throwable -> {
                    Log.d(TAG, "Login error: " + throwable.getMessage());
                    setProgressLoadingLD(false);
                    setToastMessagesLD(getApplication().getString(R.string.connection_error));
                }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
