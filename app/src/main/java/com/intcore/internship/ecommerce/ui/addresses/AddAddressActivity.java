package com.intcore.internship.ecommerce.ui.addresses;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.intcore.internship.ecommerce.BR;
import com.intcore.internship.ecommerce.R;
import com.intcore.internship.ecommerce.data.models.AddressModel;
import com.intcore.internship.ecommerce.databinding.ActivityAddAddressBinding;
import com.intcore.internship.ecommerce.ui.baseClasses.BaseActivity;
import com.intcore.internship.ecommerce.ui.baseClasses.BaseViewModel;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class AddAddressActivity extends BaseActivity<ActivityAddAddressBinding> {

    public static void startActivity(Context context){
        Intent intent = new Intent(context, AddAddressActivity.class) ;
        context.startActivity(intent);
    }

    private AddAddressViewModel addAddressViewModel;
    private ActivityAddAddressBinding activityAddAddressBinding ;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_address;
    }

    @Override
    public BaseViewModel getViewModel() {
        addAddressViewModel = ViewModelProviders.of(this,
                getCompositionRoot().getViewModelProviderFactory()).get(AddAddressViewModel.class) ;
        return addAddressViewModel;
    }

    @Nullable
    @Override
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAddAddressBinding = getViewDataBinding() ;
        setUpToolbar();
    }

    @Override
    protected void setUpObservers() {
        super.setUpObservers();
        final Observer<AddressModel> addressModelObserver = addressModel -> finishActivity();
        addAddressViewModel.getAddAddressResponseModelLD().observe(this,addressModelObserver);
    }

    private void setUpToolbar() {
        setSupportActionBar((Toolbar)activityAddAddressBinding.toolbar);

        ((TextView)activityAddAddressBinding.toolbar.findViewById(R.id.toolbarTitleTV)).setText(R.string.add_address);

        activityAddAddressBinding.toolbar.findViewById(R.id.toolbarConfirmIV).setVisibility(View.VISIBLE);
        activityAddAddressBinding.toolbar.findViewById(R.id.toolbarConfirmIV).setOnClickListener(v -> validateAndAddAddress());

        activityAddAddressBinding.toolbar.findViewById(R.id.toolbarCancelIV).setVisibility(View.VISIBLE);
        activityAddAddressBinding.toolbar.findViewById(R.id.toolbarCancelIV).setOnClickListener(v -> finishActivity());
    }

    private void validateAndAddAddress(){

        String city = activityAddAddressBinding.cityET.getText().toString() ;
        if(city.isEmpty()){
            activityAddAddressBinding.cityET.setError(getString(R.string.required_field));
            return;
        }

        String street = activityAddAddressBinding.streetET.getText().toString();
        if(street.isEmpty()){
            activityAddAddressBinding.streetET.setError(getString(R.string.required_field));
            return;
        }

        String building = activityAddAddressBinding.buildingET.getText().toString();
        if(building.isEmpty()){
            activityAddAddressBinding.buildingET.setError(getString(R.string.required_field));
            return;
        }

        String floor = activityAddAddressBinding.floorET.getText().toString() ;
        if(floor.isEmpty()){
            activityAddAddressBinding.floorET.setError(getString(R.string.required_field));
            return;
        }

        String apartment = activityAddAddressBinding.apartmentET.getText().toString() ;
        if(apartment.isEmpty()){
            activityAddAddressBinding.apartmentET.setError(getString(R.string.required_field));
            return;
        }

        String landmark = activityAddAddressBinding.landmarkET.getText().toString() ;
        if(landmark.isEmpty()){
            activityAddAddressBinding.landmarkET.setError(getString(R.string.required_field));
            return;
        }

        String phoneNumber = activityAddAddressBinding.phoneNumberET.getText().toString() ;
        if(phoneNumber.isEmpty()){
            activityAddAddressBinding.phoneNumberET.setError(getString(R.string.required_field));
            return;
        }

        String notes = activityAddAddressBinding.notesET.getText().toString() ;

        addAddressViewModel.addAddress(city,street,building,floor,apartment,landmark,phoneNumber,notes);
    }

    private void finishActivity(){
        finish();
    }
}
