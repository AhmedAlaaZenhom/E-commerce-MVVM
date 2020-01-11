package com.intcore.internship.ecommerce.ui.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.intcore.internship.ecommerce.BR;
import com.intcore.internship.ecommerce.R;
import com.intcore.internship.ecommerce.Utils.CommonUtils;
import com.intcore.internship.ecommerce.data.models.UserModel;
import com.intcore.internship.ecommerce.databinding.ActivityRegisterBinding;
import com.intcore.internship.ecommerce.ui.baseClasses.BaseActivity;
import com.intcore.internship.ecommerce.ui.baseClasses.BaseViewModel;
import com.intcore.internship.ecommerce.ui.login.LoginActivity;
import com.intcore.internship.ecommerce.ui.main.mainScreen.MainActivity;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding> {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    private ActivityRegisterBinding activityRegisterBinding ;
    private RegisterViewModel registerViewModel ;

    private int activityMode;
    private String socialID, socialType, socialName, socialEmail;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public BaseViewModel getViewModel() {
        registerViewModel = ViewModelProviders.of(this,
                getCompositionRoot().getViewModelProviderFactory()).get(RegisterViewModel.class);
        return registerViewModel;
    }

    @Nullable
    @Override
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRegisterBinding = getViewDataBinding() ;
        setUpViews();
    }

    private void setUpViews() {
        activityRegisterBinding.registerButton.setOnClickListener(v -> validateAndRegister());
        activityRegisterBinding.loginTV.setOnClickListener(v -> openLoginActivity());
    }

    @Override
    protected void setUpObservers() {
        super.setUpObservers();
        final Observer<UserModel> userModelObserver = userModel -> openMainActivity();
        registerViewModel.getRegisterResponseLD().observe(this,userModelObserver);
    }

    private void openMainActivity(){
        MainActivity.startActivity(this);
    }

    private void openLoginActivity(){
        onBackPressed();
    }

    public void validateAndRegister() {
        String name = activityRegisterBinding.nameET.getText().toString();
        String phone = activityRegisterBinding.phoneET.getText().toString();
        String email = activityRegisterBinding.emailET.getText().toString();
        String password = activityRegisterBinding.passwordET.getText().toString();

        if (TextUtils.isEmpty(name)) {
            activityRegisterBinding.nameET.setError(getString(R.string.invalid_name));
        } else if (TextUtils.isEmpty(phone)) {
            activityRegisterBinding.phoneET.setError(getString(R.string.invalid_phone));
        } else if (!CommonUtils.isEmailValid(email)) {
            activityRegisterBinding.emailET.setError(getString(R.string.invalid_email));
        } else if (TextUtils.isEmpty(password)) {
            activityRegisterBinding.passwordET.setError(getString(R.string.invalid_password));
        } else {
            registerViewModel.register(name, email, phone, password);
        }

    }
}
