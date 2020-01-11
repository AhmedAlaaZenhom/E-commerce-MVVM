package com.intcore.internship.ecommerce.ui.addresses;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
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
import com.intcore.internship.ecommerce.ui.commonClasses.ToastsHelper;

import java.util.ArrayList;
import java.util.List;

public class AddressesActivity extends BaseActivity<ActivityAddressesBinding> implements AddressRecyclerAdapter.Listener {

    private final static String ACTIVITY_MODE = "ACTIVITY_MODE";
    public final static int ACTIVITY_MODE_VIEW = 0, ACTIVITY_MODE_SELECT = 1;
    public static final int RC_SELECT_ADDRESS = 1;
    public static final String SELECTED_ADDRESS = "SELECTED_ADDRESS";

    public static void startActivity(Activity activity, int activityMode){
        Intent intent = new Intent(activity,AddressesActivity.class);
        intent.putExtra(ACTIVITY_MODE,activityMode);
        activity.startActivityForResult(intent,RC_SELECT_ADDRESS);
    }

    private int activityMode;

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

    @Nullable
    @Override
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return activityAddressesBinding.swipeRefreshLayout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAddressesBinding = getViewDataBinding() ;
        getDataFromIntent();
        setUpToolbar();
        setUpViews();
    }

    private void getDataFromIntent() {
        activityMode = getIntent().getIntExtra(ACTIVITY_MODE,ACTIVITY_MODE_VIEW);
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
        addressRecyclerAdapter = new AddressRecyclerAdapter(this, addressModelList,this) ;
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) ;
        activityAddressesBinding.addressRV.setLayoutManager(linearLayoutManager);
        activityAddressesBinding.addressRV.setAdapter(addressRecyclerAdapter);
    }

    private void openAddAddressActivity() {
        AddAddressActivity.startActivity(this);
    }

    @Override
    public void onAddressClicked(AddressModel addressModel) {
        if(activityMode==ACTIVITY_MODE_SELECT){
            toastsHelper.showMessage(new ToastsHelper.ToastMessage(getString(R.string.address_selected_successfully),ToastsHelper.MESSAGE_TYPE_SUCCESS));
            Intent intent = new Intent();
            intent.putExtra(SELECTED_ADDRESS,addressModel);
            setResult(RESULT_OK,intent);
            finish();
        }
    }
}
