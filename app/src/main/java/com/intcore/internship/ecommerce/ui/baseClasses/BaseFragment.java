package com.intcore.internship.ecommerce.ui.baseClasses;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.intcore.internship.ecommerce.di.ViewCompositionRoot;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

public abstract class BaseFragment<T extends ViewDataBinding> extends Fragment {

    private BaseActivity mActivity;
    private BaseViewModel mViewModel;
    private T mViewDataBinding;

    public abstract int getBindingVariable();

    public abstract @LayoutRes int getLayoutId();

    public abstract BaseViewModel getViewModel();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            this.mActivity = (BaseActivity) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = getViewModel();
        setHasOptionsMenu(false);
    }

    protected void setUpObservers(){
        final Observer<Boolean> progressLoadingObserver = loading -> {
            if(loading)
                mActivity.progressHudHelper.showProgressHud();
            else
                mActivity.progressHudHelper.hideProgressHud();
        };
        mViewModel.getProgressLoadingLD().observe(this,progressLoadingObserver);

        final Observer<String> toastMessagesObserver = message -> {
            mActivity.toastsHelper.showMessageError(message);
        };
        mViewModel.getToastMessagesLD().observe(this,toastMessagesObserver);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        setUpObservers();
        return mViewDataBinding.getRoot();
    }

    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
        mViewDataBinding.setLifecycleOwner(this);
        mViewDataBinding.executePendingBindings();
    }

    public BaseActivity getBaseActivity() {
        return mActivity;
    }

    public ViewCompositionRoot getCompositionRoot(){
        return mActivity.getCompositionRoot() ;
    }

    protected T getViewDataBinding() {
        return mViewDataBinding;
    }

    public void hideKeyboard() {
        if (mActivity != null) {
            mActivity.hideKeyboard();
        }
    }
}
