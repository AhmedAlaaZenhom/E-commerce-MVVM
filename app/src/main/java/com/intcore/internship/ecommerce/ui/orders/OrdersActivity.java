package com.intcore.internship.ecommerce.ui.orders;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.intcore.internship.ecommerce.BR;
import com.intcore.internship.ecommerce.R;
import com.intcore.internship.ecommerce.data.models.OrderModel;
import com.intcore.internship.ecommerce.data.sharedPreferences.PreferenceHelper;
import com.intcore.internship.ecommerce.databinding.ActivityOrdersBinding;
import com.intcore.internship.ecommerce.ui.baseClasses.BaseActivity;
import com.intcore.internship.ecommerce.ui.baseClasses.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class OrdersActivity extends BaseActivity<ActivityOrdersBinding> {

    public static void startActivity(Context context){
        Intent intent = new Intent(context,OrdersActivity.class);
        context.startActivity(intent);
    }

    private OrdersViewModel ordersViewModel ;
    private ActivityOrdersBinding activityOrdersBinding ;

    private OrdersRecyclerAdapter ordersRecyclerAdapter;
    private ArrayList<OrderModel> orderModelArrayList;

    private boolean isCurrentLocaleAR;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_orders;
    }

    @Override
    public BaseViewModel getViewModel() {
        ordersViewModel = ViewModelProviders.of(this,
                getCompositionRoot().getViewModelProviderFactory()).get(OrdersViewModel.class) ;
        return ordersViewModel;
    }

    @Nullable
    @Override
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityOrdersBinding = getViewDataBinding() ;
        isCurrentLocaleAR = ordersViewModel.getSavedLocale().equals(PreferenceHelper.LOCALE_ARABIC);
        initToolbar();
        setUpViews();
        ordersViewModel.getOrders();
    }

    private void initToolbar() {
        setSupportActionBar((Toolbar)activityOrdersBinding.toolbar);
        ((TextView)activityOrdersBinding.toolbar.findViewById(R.id.toolbarTitleTV)).setText(R.string.orders);
        activityOrdersBinding.toolbar.findViewById(R.id.toolbarBackBtn).setVisibility(View.VISIBLE);
        activityOrdersBinding.toolbar.findViewById(R.id.toolbarBackBtn).setOnClickListener(v -> onBackPressed());
    }

    private void setUpViews() {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) ;
        activityOrdersBinding.ordersRV.setLayoutManager(linearLayoutManager);
        orderModelArrayList = new ArrayList<>() ;
        ordersRecyclerAdapter = new OrdersRecyclerAdapter(this,isCurrentLocaleAR,orderModelArrayList);
        activityOrdersBinding.ordersRV.setAdapter(ordersRecyclerAdapter);
    }

    @Override
    protected void setUpObservers() {
        super.setUpObservers();
        final Observer<List<OrderModel>> orderListObserver = orderModels -> {
            orderModelArrayList.clear();
            orderModelArrayList.addAll(orderModels) ;
            ordersRecyclerAdapter.notifyDataSetChanged();
        };
        ordersViewModel.getOrderModelListLD().observe(this,orderListObserver);
    }
}
