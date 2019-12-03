package com.intcore.internship.ecommerce.ui.addresses;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.intcore.internship.ecommerce.BR;
import com.intcore.internship.ecommerce.R;
import com.intcore.internship.ecommerce.data.models.AddressModel;
import com.intcore.internship.ecommerce.databinding.ActivityAddressesBinding;
import com.intcore.internship.ecommerce.ui.baseClasses.BaseActivity;
import com.intcore.internship.ecommerce.ui.baseClasses.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class AddressesActivity extends BaseActivity<ActivityAddressesBinding> {

    public static void startActivity(Context context){
        Intent intent = new Intent(context,AddressesActivity.class);
        context.startActivity(intent);
    }

    private AddressesViewModel addressesViewModel ;
    private ActivityAddressesBinding activityAddressesBinding ;

    private AddressRecyclerAdapter addressRecyclerAdapter ;
    private ArrayList<AddressModel> addressModelList ;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_addresses;
    }

    @Override
    public BaseViewModel getViewModel() {
        addressesViewModel = ViewModelProviders.of(this,
                getCompositionRoot().getViewModelProviderFactory()).get(AddressesViewModel.class) ;
        return addressesViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAddressesBinding = getViewDataBinding() ;
        setUpToolbar();
        setUpViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
        addressesViewModel.getAddresses();
    }

    @Override
    protected void setUpObservers() {
        super.setUpObservers();
        final Observer<List<AddressModel>> addressesObserver = addressModels -> {
            addressModelList.clear();
            addressModelList.addAll(addressModels);
            addressRecyclerAdapter.notifyDataSetChanged();
        };
        addressesViewModel.getAddressesLD().observe(this,addressesObserver);
    }

    private void setUpToolbar() {
        setSupportActionBar((Toolbar) activityAddressesBinding.toolbar);
        ((TextView)activityAddressesBinding.toolbar.findViewById(R.id.toolbarTitleTV)).setText(R.string.my_addresses);
        activityAddressesBinding.toolbar.findViewById(R.id.toolbarBackBtn).setVisibility(View.VISIBLE);
        activityAddressesBinding.toolbar.findViewById(R.id.toolbarBackBtn).setOnClickListener(v -> onBackPressed());
    }

    private void setUpViews() {
        activityAddressesBinding.swipeRefreshLayout.setOnRefreshListener(() -> addressesViewModel.getAddresses());
        activityAddressesBinding.floatingActionButton.setOnClickListener(v -> openAddAddressActivity());
        addressModelList = new ArrayList<>() ;
        addressRecyclerAdapter = new AddressRecyclerAdapter(addressModelList) ;
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) ;
        activityAddressesBinding.addressRV.setLayoutManager(linearLayoutManager);
        activityAddressesBinding.addressRV.setAdapter(addressRecyclerAdapter);
    }

    private void openAddAddressActivity() {
        AddAddressActivity.startActivity(this);
    }

}
