package com.intcore.internship.ecommerce.ui.login;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.intcore.internship.ecommerce.R;
import com.intcore.internship.ecommerce.Utils.CommonUtils;
import com.intcore.internship.ecommerce.data.models.UserModel;
import com.intcore.internship.ecommerce.databinding.ActivityLoginBinding;
import com.intcore.internship.ecommerce.ui.baseClasses.BaseActivity;
import com.intcore.internship.ecommerce.ui.commonClasses.ToastsHelper;
import com.intcore.internship.ecommerce.ui.main.mainScreen.MainActivity;
import com.intcore.internship.ecommerce.ui.register.RegisterActivity;


public class LoginActivity extends BaseActivity<ActivityLoginBinding> {

    private static final String TAG = LoginActivity.class.getSimpleName() ;
    private static final int RC_SIGN_IN = 1 ;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    private ActivityLoginBinding activityLoginBinding;
    private LoginViewModel loginViewModel;
    private long lastTimeBackPressed;

    private GoogleSignInClient googleSignInClient;

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
                getCompositionRoot().getViewModelProviderFactory()).get(LoginViewModel.class);
        return loginViewModel;
    }

    @Nullable
    @Override
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = getViewDataBinding();

        googleSignInClient = loginViewModel.initGoogleLogin(this);

        setUpViews();
    }

    private void setUpViews() {
        activityLoginBinding.loginButton.setOnClickListener(v -> validateAndLogin());
        activityLoginBinding.signUpTv.setOnClickListener(v -> openSignUpActivity());

        final View.OnClickListener onClickListener = v -> startGoogleLoginActivity();
        activityLoginBinding.googleLoginTV.setOnClickListener(onClickListener);
        activityLoginBinding.googleLoginIV.setOnClickListener(onClickListener);
    }

    @Override
    protected void setUpObservers() {
        super.setUpObservers();
        final Observer<UserModel> userModelObserver = userModel -> openMainActivity();
        loginViewModel.getLoginResponseLD().observe(this, userModelObserver);
    }

    public void openMainActivity() {
        MainActivity.startActivity(this);
    }

    public void openSignUpActivity() {
        RegisterActivity.startActivity(this);
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

    @Override
    public void onBackPressed() {
        final long timeNow = System.currentTimeMillis();
        if (timeNow - lastTimeBackPressed > 2000) {
            lastTimeBackPressed = timeNow;
            toastsHelper.showMessage(new ToastsHelper.ToastMessage(getString(R.string.press_again_to_exit), ToastsHelper.MESSAGE_TYPE_INFO));
        } else {
            super.onBackPressed();
        }
    }


    private void startGoogleLoginActivity() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        progressHudHelper.showProgressHud();
        // Handle google login
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if(account!=null){
                    Log.d(TAG,"Google sign in success: "+account.getId());
                    progressHudHelper.hideProgressHud();
                    toastsHelper.showUnknownError();
                    //loginViewModel.loginUsingSocialMedia(account.getId(),"google");
                }
            } catch (ApiException e) {
                Log.d(TAG, "Google sign in failed", e);
                progressHudHelper.hideProgressHud();
            }
        }
    }

}

