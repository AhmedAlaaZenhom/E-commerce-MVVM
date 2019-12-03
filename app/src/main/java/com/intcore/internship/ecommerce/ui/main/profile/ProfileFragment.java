package com.intcore.internship.ecommerce.ui.main.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.intcore.internship.ecommerce.BR;
import com.intcore.internship.ecommerce.R;
import com.intcore.internship.ecommerce.databinding.FragmentMainProfileBinding;
import com.intcore.internship.ecommerce.ui.addresses.AddressesActivity;
import com.intcore.internship.ecommerce.ui.baseClasses.BaseFragment;
import com.intcore.internship.ecommerce.ui.baseClasses.BaseViewModel;
import com.intcore.internship.ecommerce.ui.orders.OrdersActivity;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

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
        profileViewModel = ViewModelProviders.of(requireActivity(),
                getCompositionRoot().getViewModelProviderFactory()).get(ProfileViewModel.class) ;
        return profileViewModel;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  super.onCreateView(inflater, container, savedInstanceState);
        fragmentMainProfileBinding = getViewDataBinding() ;
        setUpViews();
        return view ;
    }

    private void setUpViews() {
        fragmentMainProfileBinding.myOrdersCV.setOnClickListener(v -> openOrdersActivity());
        fragmentMainProfileBinding.myAddressesCV.setOnClickListener(v -> openAddressesActivity());
    }

    private void openOrdersActivity(){
        OrdersActivity.startActivity(requireContext());
    }

    private void openAddressesActivity(){
        AddressesActivity.startActivity(requireContext());
    }
}
