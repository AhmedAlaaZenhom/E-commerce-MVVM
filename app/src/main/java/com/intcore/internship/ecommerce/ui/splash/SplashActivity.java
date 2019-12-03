package com.intcore.internship.ecommerce.ui.splash;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;

import com.intcore.internship.ecommerce.BR;
import com.intcore.internship.ecommerce.R;
import com.intcore.internship.ecommerce.databinding.ActivitySplashBinding;
import com.intcore.internship.ecommerce.ui.baseClasses.BaseActivity;
import com.intcore.internship.ecommerce.ui.login.LoginActivity;
import com.intcore.internship.ecommerce.ui.main.mainScreen.MainActivity;

public class SplashActivity extends BaseActivity<ActivitySplashBinding> {

    private SplashViewModel mSplashViewModel;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSplashViewModel.decideNextActivity();
    }

    @Override
    protected void setUpObservers() {
        super.setUpObservers();
        final Observer<Boolean> loggedInMoodObserver = isLoggedIn -> {
            if (isLoggedIn)
                openMainActivity();
            else
                openLoginActivity();
        };
        mSplashViewModel.getIsLoggedInMood().observe(this, loggedInMoodObserver);
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
