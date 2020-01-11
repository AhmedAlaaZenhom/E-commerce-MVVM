package com.intcore.internship.ecommerce.ui.checkout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.intcore.internship.ecommerce.BR;
import com.intcore.internship.ecommerce.R;
import com.intcore.internship.ecommerce.data.remote.helperModels.cart.CartsResponseModel;
import com.intcore.internship.ecommerce.data.remote.helperModels.order.OrderResponseModel;
import com.intcore.internship.ecommerce.data.models.AddressModel;
import com.intcore.internship.ecommerce.data.models.CartItemModel;
import com.intcore.internship.ecommerce.data.sharedPreferences.PreferenceHelper;
import com.intcore.internship.ecommerce.databinding.ActivityCheckoutBinding;
import com.intcore.internship.ecommerce.ui.addresses.AddressesActivity;
import com.intcore.internship.ecommerce.ui.baseClasses.BaseActivity;
import com.intcore.internship.ecommerce.ui.baseClasses.BaseViewModel;
import com.intcore.internship.ecommerce.ui.commonClasses.Utils;
import com.intcore.internship.ecommerce.ui.orders.OrdersActivity;
import com.vinay.stepview.HorizontalStepView;
import com.vinay.stepview.models.Step;

import java.util.ArrayList;
import java.util.List;

public class CheckoutActivity extends BaseActivity<ActivityCheckoutBinding> {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, CheckoutActivity.class);
        context.startActivity(intent);
    }

    private static final int LAYOUT_SHIPPING = 0, LAYOUT_PAYMENT = 1, LAYOUT_CONFIRMATION = 2;
    private int currentLayout;

    private CheckoutViewModel checkoutViewModel;
    private ActivityCheckoutBinding activityCheckoutBinding;

    private HorizontalStepView stepView;
    private ShipmentRecyclerAdapter shipmentRecyclerAdapter;
    private ArrayList<CartItemModel> cartItemModelArrayList;

    private AddressModel currentAddressModel;

    private boolean isCurrentLocaleAR;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_checkout;
    }

    @Override
    public BaseViewModel getViewModel() {
        checkoutViewModel = ViewModelProviders.of(this,
                getCompositionRoot().getViewModelProviderFactory()).get(CheckoutViewModel.class);
        return checkoutViewModel;
    }

    @Nullable
    @Override
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCheckoutBinding = getViewDataBinding();
        isCurrentLocaleAR = checkoutViewModel.getSavedLocale().equals(PreferenceHelper.LOCALE_ARABIC);

        setUpToolbar();
        setUpViews();
        moveToLayout(LAYOUT_SHIPPING);
        checkoutViewModel.getAddresses();
        checkoutViewModel.getCarts();
    }

    @Override
    public void onBackPressed() {
        if (currentLayout == LAYOUT_PAYMENT)
            moveToLayout(LAYOUT_SHIPPING);
        else
            super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== AddressesActivity.RC_SELECT_ADDRESS && resultCode == RESULT_OK && data!=null){
            final AddressModel addressModel = (AddressModel) data.getSerializableExtra(AddressesActivity.SELECTED_ADDRESS);
            if (addressModel != null) {
                bindAddressesData(addressModel);
            }
        }
    }

    @Override
    protected void setUpObservers() {
        super.setUpObservers();

        final Observer<List<AddressModel>> addressesObserver = addressModelList -> bindAddressesData(addressModelList.get(0));
        checkoutViewModel.getAddressesLD().observe(this, addressesObserver);

        final Observer<CartsResponseModel> cartsObserver = this::bindCartData;
        checkoutViewModel.getCartResponseLD().observe(this, cartsObserver);

        final Observer<OrderResponseModel> orderResponseModelObserver = orderResponseModel -> moveToLayout(LAYOUT_CONFIRMATION);
        checkoutViewModel.getOrderResponseModelLD().observe(this, orderResponseModelObserver);
    }

    private void setUpToolbar() {
        setSupportActionBar((Toolbar) activityCheckoutBinding.toolbar);
        ((TextView) activityCheckoutBinding.toolbar.findViewById(R.id.toolbarTitleTV)).setText(R.string.checkout);
        activityCheckoutBinding.toolbar.findViewById(R.id.toolbarBackBtn).setVisibility(View.VISIBLE);
        activityCheckoutBinding.toolbar.findViewById(R.id.toolbarBackBtn).setOnClickListener(v -> onBackPressed());
    }

    private void setUpViews() {
        setUpStepView();

        activityCheckoutBinding.changeAddressTV.setOnClickListener(v -> AddressesActivity.startActivity(CheckoutActivity.this,AddressesActivity.ACTIVITY_MODE_SELECT));

        cartItemModelArrayList = new ArrayList<>();
        shipmentRecyclerAdapter = new ShipmentRecyclerAdapter(isCurrentLocaleAR, this, cartItemModelArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        activityCheckoutBinding.cartRV.setLayoutManager(linearLayoutManager);
        activityCheckoutBinding.cartRV.setAdapter(shipmentRecyclerAdapter);

        activityCheckoutBinding.continueToPaymentTV.setOnClickListener(v -> moveToLayout(LAYOUT_PAYMENT));

        activityCheckoutBinding.cashOnDeliveryCV.setOnClickListener(v -> showCashOnDeliveryConfirmationDialog());

        activityCheckoutBinding.trackOrderBtn.setOnClickListener(v -> openOrdersActivity());
    }

    private void setUpStepView() {
        stepView = activityCheckoutBinding.stepView;
        List<Step> checkoutStatusList = new ArrayList<>();
        Step shipping = new Step(getString(R.string.shipping), Step.State.COMPLETED);
        Step payment = new Step(getString(R.string.payment), Step.State.NOT_COMPLETED);
        Step confirmation = new Step(getString(R.string.confirmation), Step.State.NOT_COMPLETED);
        checkoutStatusList.add(shipping);
        checkoutStatusList.add(payment);
        checkoutStatusList.add(confirmation);
        stepView.setSteps(checkoutStatusList);
        stepView.setTextSize(12);
        stepView.setNotCompletedLineColor(Color.GRAY);
        stepView.setCompletedLineColor(getResources().getColor(R.color.colorPrimary));
        stepView.setNotCompletedStepTextColor(Color.GRAY);
        stepView.setCompletedStepTextColor(Color.BLACK);
        final GradientDrawable shapeNotCompleted = new GradientDrawable();
        shapeNotCompleted.setShape(GradientDrawable.OVAL);
        shapeNotCompleted.setColor(Color.GRAY);
        stepView.setCompletedStepIcon(getResources().getDrawable(R.drawable.checked));
        stepView.setNotCompletedStepIcon(shapeNotCompleted);

        int screenWidth = Utils.getDisplayMetrics(this).widthPixels;
        stepView.setLineLengthPx((int) (screenWidth * 0.27));
        stepView.setCircleRadiusPx((int) (screenWidth * 0.027));
    }

    private void moveToLayout(int layout) {
        this.currentLayout = layout;
        switch (layout) {
            case LAYOUT_SHIPPING:
                activityCheckoutBinding.shippingLayout.setVisibility(View.VISIBLE);
                activityCheckoutBinding.paymentLayout.setVisibility(View.GONE);
                activityCheckoutBinding.confirmationLayout.setVisibility(View.GONE);
                stepView.setStepState(Step.State.COMPLETED, 0);
                stepView.setStepState(Step.State.NOT_COMPLETED, 1);
                stepView.setStepState(Step.State.NOT_COMPLETED, 2);
                break;
            case LAYOUT_PAYMENT:
                activityCheckoutBinding.paymentLayout.setVisibility(View.VISIBLE);
                activityCheckoutBinding.shippingLayout.setVisibility(View.GONE);
                activityCheckoutBinding.confirmationLayout.setVisibility(View.GONE);
                stepView.setStepState(Step.State.COMPLETED, 0);
                stepView.setStepState(Step.State.COMPLETED, 1);
                stepView.setStepState(Step.State.NOT_COMPLETED, 2);
                break;
            case LAYOUT_CONFIRMATION:
                activityCheckoutBinding.confirmationLayout.setVisibility(View.VISIBLE);
                activityCheckoutBinding.shippingLayout.setVisibility(View.GONE);
                activityCheckoutBinding.paymentLayout.setVisibility(View.GONE);
                stepView.setStepState(Step.State.COMPLETED, 0);
                stepView.setStepState(Step.State.COMPLETED, 1);
                stepView.setStepState(Step.State.COMPLETED, 2);
                break;

        }
    }

    private void bindAddressesData(AddressModel addressModel) {
        this.currentAddressModel = addressModel;
        activityCheckoutBinding.shortAddressTV.setText(addressModel.getCity());
        activityCheckoutBinding.longAddressTV.setText(addressModel.getApartment() + " " + addressModel.getStreet() + ", " + addressModel.getCity() + ".");
    }

    private void bindCartData(CartsResponseModel cartsResponseModel) {
        cartItemModelArrayList.clear();
        cartItemModelArrayList.addAll(cartsResponseModel.getCartItemModelList());
        shipmentRecyclerAdapter.notifyDataSetChanged();

        activityCheckoutBinding.itemsCountTV.setText(cartsResponseModel.getTotalCount() + " " + getString(R.string.items));
        activityCheckoutBinding.itemsPriceTV.setText("$" + String.format("%.1f", cartsResponseModel.getTotalPrice()));
        activityCheckoutBinding.shippingPriceTV.setText("$" + String.valueOf(cartsResponseModel.getShipping()));
        activityCheckoutBinding.totalPriceTV.setText("$" + String.format("%.1f", cartsResponseModel.getTotalPrice()));
    }

    private void showCashOnDeliveryConfirmationDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.cash_on_delivery);
        builder.setMessage(R.string.place_order_message);
        builder.setPositiveButton(R.string.place_order, (dialog, which) -> createOrder());
        builder.setNeutralButton(R.string.cancel, (dialog, which) -> {
        });
        builder.create().show();
    }

    private void createOrder() {
        checkoutViewModel.createOrder(currentAddressModel.getId());
    }

    private void openOrdersActivity() {
        OrdersActivity.startActivity(this);
        finish();
    }

}
