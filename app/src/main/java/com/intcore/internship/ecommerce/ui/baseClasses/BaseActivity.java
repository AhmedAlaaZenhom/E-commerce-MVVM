package com.intcore.internship.ecommerce.ui.baseClasses;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.intcore.internship.ecommerce.ApplicationClass;
import com.intcore.internship.ecommerce.di.ViewCompositionRoot;
import com.intcore.internship.ecommerce.ui.commonClasses.ProgressHudHelper;
import com.intcore.internship.ecommerce.ui.commonClasses.ToastsHelper;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public abstract class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity {

    private ViewCompositionRoot viewCompositionRoot;
    private T mViewDataBinding;
    private BaseViewModel mViewModel;
    public ProgressHudHelper progressHudHelper;
    public ToastsHelper toastsHelper ;

    public abstract int getBindingVariable();

    public abstract @LayoutRes int getLayoutId();

    public abstract BaseViewModel getViewModel();

    @Nullable
    public abstract SwipeRefreshLayout getSwipeRefreshLayout();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        progressHudHelper = getCompositionRoot().getProgressHudHelper() ;
        toastsHelper = getCompositionRoot().getToastsHelper() ;

        performDataBinding();
        setUpObservers();
    }

    protected ViewCompositionRoot getCompositionRoot() {
        if (viewCompositionRoot == null) {
            viewCompositionRoot = new ViewCompositionRoot(
                    ((ApplicationClass) getApplication()).getCompositionRoot(),
                    this
            );
        }
        return viewCompositionRoot;
    }

    private void performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        this.mViewModel = mViewModel == null ? getViewModel() : mViewModel;
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
        mViewDataBinding.executePendingBindings();
    }

    protected void setUpObservers(){
        final Observer<Boolean> progressLoadingObserver = loading -> {
            if(loading)
                progressHudHelper.showProgressHud();
            else
                progressHudHelper.hideProgressHud();
        };
        mViewModel.getProgressLoadingLD().observe(this,progressLoadingObserver);

        final Observer<ToastsHelper.ToastMessage> toastMessagesObserver = message -> {
            toastsHelper.showMessage(message);
        };
        mViewModel.getToastMessagesLD().observe(this,toastMessagesObserver);

        final Observer<Boolean> swipeRefreshLoadingObserver = loading -> {
            if(getSwipeRefreshLayout()!=null)
                getSwipeRefreshLayout().setRefreshing(loading);
        };
        mViewModel.getSwipeRefreshLoadingLD().observe(this,swipeRefreshLoadingObserver);
    }

    protected T getViewDataBinding() {
        return mViewDataBinding;
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

}
