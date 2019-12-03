package com.intcore.internship.ecommerce.ui.login;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.intcore.internship.ecommerce.R;
import com.intcore.internship.ecommerce.Utils.CommonUtils;
import com.intcore.internship.ecommerce.data.models.UserModel;
import com.intcore.internship.ecommerce.databinding.ActivityLoginBinding;
import com.intcore.internship.ecommerce.ui.baseClasses.BaseActivity;
import com.intcore.internship.ecommerce.ui.main.mainScreen.MainActivity;
import com.intcore.internship.ecommerce.ui.register.RegisterActivity;


public class LoginActivity extends BaseActivity<ActivityLoginBinding>{

    public static void startActivity(Context context){
        Intent intent = new Intent(context,LoginActivity.class) ;
        context.startActivity(intent);
    }

    private ActivityLoginBinding activityLoginBinding ;
    private LoginViewModel loginViewModel ;

    @Override
    public int getBindingVariable() {
        return com.intcore.internship.ecommerce.BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public LoginViewModel getViewModel() {
        loginViewModel = ViewModelProviders.of(this,
                getCompositionRoot().getViewModelProviderFactory()).get(LoginViewModel.class) ;
        return loginViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = getViewDataBinding();
        setUpViews() ;
    }

    private void setUpViews() {
        activityLoginBinding.loginButton.setOnClickListener(v -> validateAndLogin());
        activityLoginBinding.signUpTv.setOnClickListener(v -> openSignUpActivity());
    }

    @Override
    protected void setUpObservers(){
        super.setUpObservers();
        final Observer<UserModel> userModelObserver = userModel -> openMainActivity();
        loginViewModel.getLoginResponseLD().observe(this,userModelObserver);
    }

    public void openMainActivity() {
        MainActivity.startActivity(this);
        finish();
    }

    public void openSignUpActivity() {
        RegisterActivity.startActivity(this);
        finish();
    }

    public void validateAndLogin() {
        String email = activityLoginBinding.emailET.getText().toString();
        String password = activityLoginBinding.passwordET.getText().toString();
        if (!CommonUtils.isEmailValid(email)) {
            activityLoginBinding.emailET.setError(getString(R.string.invalid_email));
        } else if (TextUtils.isEmpty(password)) {
            activityLoginBinding.passwordET.setError(getString(R.string.invalid_password));
        } else {
            loginViewModel.loginUsingEmailAndPassword(email, password);
        }
    }

}
