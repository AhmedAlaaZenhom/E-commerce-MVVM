package com.intcore.internship.ecommerce.ui.splash;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.intcore.internship.ecommerce.BR;
import com.intcore.internship.ecommerce.R;
import com.intcore.internship.ecommerce.databinding.ActivitySplashBinding;
import com.intcore.internship.ecommerce.ui.baseClasses.BaseActivity;
import com.intcore.internship.ecommerce.ui.login.LoginActivity;
import com.intcore.internship.ecommerce.ui.main.mainScreen.MainActivity;

public class SplashActivity extends BaseActivity<ActivitySplashBinding> {

    private SplashViewModel mSplashViewModel;
    private boolean isFinished = false;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public SplashViewModel getViewModel() {
        mSplashViewModel = ViewModelProviders.of(this,
                getCompositionRoot().getViewModelProviderFactory()).get(SplashViewModel.class);
        return mSplashViewModel;
    }

    @Nullable
    @Override
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(() -> {
            if (!isFinished)
                decideNextActivity();
        }, 1000);
    }

    @Override
    protected void onDestroy() {
        isFinished = true;
        super.onDestroy();
    }

    private void decideNextActivity() {
        if (mSplashViewModel.isUserLoggedIn())
            openMainActivity();
        else
            openLoginActivity();
    }

    private void openLoginActivity() {
        LoginActivity.startActivity(this);
        finish();
    }

    private void openMainActivity() {
        MainActivity.startActivity(this);
        finish();
    }

}
