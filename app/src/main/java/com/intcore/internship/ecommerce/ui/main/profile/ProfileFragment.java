package com.intcore.internship.ecommerce.ui.main.profile;

import android.app.ActivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.intcore.internship.ecommerce.BR;
import com.intcore.internship.ecommerce.R;
import com.intcore.internship.ecommerce.data.models.UserModel;
import com.intcore.internship.ecommerce.data.remote.helperModels.profile.ProfileResponseModel;
import com.intcore.internship.ecommerce.data.sharedPreferences.PreferenceHelper;
import com.intcore.internship.ecommerce.databinding.FragmentMainProfileBinding;
import com.intcore.internship.ecommerce.ui.addresses.AddressesActivity;
import com.intcore.internship.ecommerce.ui.baseClasses.BaseFragment;
import com.intcore.internship.ecommerce.ui.baseClasses.BaseViewModel;
import com.intcore.internship.ecommerce.ui.orders.OrdersActivity;
import com.jakewharton.processphoenix.ProcessPhoenix;
import com.yariksoffice.lingver.Lingver;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import static android.content.Context.ACTIVITY_SERVICE;

public class ProfileFragment extends BaseFragment<FragmentMainProfileBinding> {

    public static final String TAG = ProfileFragment.class.getSimpleName() ;

    public static ProfileFragment newInstance() {
        Bundle args = new Bundle();
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private ProfileViewModel profileViewModel ;
    private FragmentMainProfileBinding fragmentMainProfileBinding ;

    private boolean isCurrentLocaleAR;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_profile;
    }

    @Override
    public BaseViewModel getViewModel() {
        profileViewModel = ViewModelProviders.of(this,
                getCompositionRoot().getViewModelProviderFactory()).get(ProfileViewModel.class) ;
        return profileViewModel;
    }

    @Nullable
    @Override
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return null;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  super.onCreateView(inflater, container, savedInstanceState);
        fragmentMainProfileBinding = getViewDataBinding() ;
        isCurrentLocaleAR = profileViewModel.getSavedLocale().equals(PreferenceHelper.LOCALE_ARABIC);
        setUpViews();
        profileViewModel.getProfile();
        return view ;
    }

    @Override
    protected void setUpObservers() {
        super.setUpObservers();
        final Observer<ProfileResponseModel> profileObserver = profileResponseModel -> {
            final UserModel userModel = profileResponseModel.getUserModel();
            fragmentMainProfileBinding.userNameTV.setText(userModel.getName());
            fragmentMainProfileBinding.userEmailTV.setText(userModel.getEmail());
        };
        profileViewModel.getProfileResponseLD().observe(this, profileObserver);
    }

    private void setUpViews() {
        fragmentMainProfileBinding.myOrdersCV.setOnClickListener(v -> openOrdersActivity());
        fragmentMainProfileBinding.myAddressesCV.setOnClickListener(v -> openAddressesActivity());
        initLanguageViews();
        initLogoutViews();
    }

    private void initLanguageViews() {
        String[] values = {getString(R.string.arabic), getString(R.string.english)};
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(R.string.select_language);
        builder.setSingleChoiceItems(values, isCurrentLocaleAR ? 0 : 1, (dialog, item) -> {

            switch (item) {
                case 0:
                    changeLanguageAndRestart(PreferenceHelper.LOCALE_ARABIC);
                    break;
                case 1:
                    changeLanguageAndRestart(PreferenceHelper.LOCALE_ENGLISH);
                    break;
            }
            dialog.dismiss();
        });
        AlertDialog languageAlertDialog = builder.create();
        fragmentMainProfileBinding.languageCV.setOnClickListener(view -> languageAlertDialog.show());
    }

    private void changeLanguageAndRestart(String locale) {
        Lingver.getInstance().setLocale(requireContext(), locale);
        profileViewModel.changeLocale(locale);
        restartApplication();
    }

    private void restartApplication() {
        ProcessPhoenix.triggerRebirth(requireContext());
    }

    private void initLogoutViews() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(R.string.log_out);
        builder.setMessage(R.string.logout_question);
        builder.setPositiveButton(R.string.log_out, (dialogInterface, i) -> {
            try {
                ((ActivityManager) Objects.requireNonNull(requireContext().getSystemService(ACTIVITY_SERVICE))).clearApplicationUserData();
            } catch (NullPointerException e) {
                Log.d(TAG, e.toString());
            }
        });
        builder.setNegativeButton(R.string.cancel, (dialogInterface, i) -> {

        });
        AlertDialog signOutAlertDialog = builder.create();
        fragmentMainProfileBinding.logoutCV.setOnClickListener(view -> signOutAlertDialog.show());
    }

    private void openOrdersActivity(){
        OrdersActivity.startActivity(requireContext());
    }

    private void openAddressesActivity(){
        AddressesActivity.startActivity(requireActivity(),AddressesActivity.ACTIVITY_MODE_VIEW);
    }

}
