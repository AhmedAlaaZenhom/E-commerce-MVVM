package com.intcore.internship.ecommerce.ui.baseClasses;

import android.app.Application;

import com.intcore.internship.ecommerce.ApplicationClass;
import com.intcore.internship.ecommerce.di.ViewModelCompositionRoot;
import com.intcore.internship.ecommerce.ui.commonClasses.ToastsHelper;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.disposables.CompositeDisposable;

public class BaseViewModel extends AndroidViewModel {

    private ViewModelCompositionRoot viewModelCompositionRoot ;
    private CompositeDisposable mCompositeDisposable;

    private MutableLiveData<Boolean> progressLoadingLD ;
    private MutableLiveData<Boolean> swipeRefreshLoadingLD ;
    private MutableLiveData<ToastsHelper.ToastMessage> toastMessagesLD ;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        this.mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void onCleared() {
        mCompositeDisposable.dispose();
        super.onCleared();
    }

    protected ViewModelCompositionRoot getCompositionRoot() {
        if (viewModelCompositionRoot == null)
            viewModelCompositionRoot = new ViewModelCompositionRoot(
                    ((ApplicationClass) getApplication()).getCompositionRoot());

        return viewModelCompositionRoot;
    }

    protected CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    MutableLiveData<Boolean> getProgressLoadingLD() {
        if(progressLoadingLD==null)
            progressLoadingLD = new MutableLiveData<>() ;
        return progressLoadingLD;
    }

    protected void setProgressLoadingLD(boolean loading) {
        getProgressLoadingLD().setValue(loading);
    }

    MutableLiveData<Boolean> getSwipeRefreshLoadingLD() {
        if(swipeRefreshLoadingLD==null)
            swipeRefreshLoadingLD = new MutableLiveData<>() ;
        return swipeRefreshLoadingLD;
    }

    protected void setToastMessagesLD(ToastsHelper.ToastMessage message) {
        getToastMessagesLD().setValue(message);
    }

    MutableLiveData<ToastsHelper.ToastMessage> getToastMessagesLD() {
        if(toastMessagesLD==null)
            toastMessagesLD = new MutableLiveData<>() ;
        return toastMessagesLD;
    }

    protected void setSwipeRefreshLoadingLD(Boolean isLoading) {
        getSwipeRefreshLoadingLD().setValue(isLoading);
    }
}
