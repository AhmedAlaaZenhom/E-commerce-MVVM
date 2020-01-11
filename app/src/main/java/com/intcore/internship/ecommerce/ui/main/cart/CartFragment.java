package com.intcore.internship.ecommerce.ui.main.cart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.intcore.internship.ecommerce.BR;
import com.intcore.internship.ecommerce.R;
import com.intcore.internship.ecommerce.data.remote.helperModels.cart.CartsResponseModel;
import com.intcore.internship.ecommerce.data.models.AddressModel;
import com.intcore.internship.ecommerce.data.models.CartItemModel;
import com.intcore.internship.ecommerce.data.sharedPreferences.PreferenceHelper;
import com.intcore.internship.ecommerce.databinding.FragmentMainCartBinding;
import com.intcore.internship.ecommerce.ui.baseClasses.BaseFragment;
import com.intcore.internship.ecommerce.ui.baseClasses.BaseViewModel;
import com.intcore.internship.ecommerce.ui.checkout.CheckoutActivity;
import com.intcore.internship.ecommerce.ui.commonClasses.ToastsHelper;
import com.intcore.internship.ecommerce.ui.main.mainScreen.MainActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class CartFragment extends BaseFragment<FragmentMainCartBinding>
        implements CartRecyclerAdapter.ClickListener {

    public static final String TAG = CartFragment.class.getSimpleName();

    public static CartFragment newInstance() {
        Bundle args = new Bundle();
        CartFragment fragment = new CartFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private CartViewModel cartViewModel;
    private FragmentMainCartBinding fragmentMainCartBinding;

    private CartRecyclerAdapter cartRecyclerAdapter;
    private ArrayList<CartItemModel> cartItemModelArrayList;

    private boolean isCurrentLocaleAR;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_cart;
    }

    @Override
    public BaseViewModel getViewModel() {
        cartViewModel = ViewModelProviders.of(this,
                getCompositionRoot().getViewModelProviderFactory()).get(CartViewModel.class);
        return cartViewModel;
    }

    @Nullable
    @Override
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return fragmentMainCartBinding.swipeRefreshLayout;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        fragmentMainCartBinding = getViewDataBinding();
        isCurrentLocaleAR = cartViewModel.getSavedLocale().equals(PreferenceHelper.LOCALE_ARABIC);
        setUpViews();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        cartViewModel.getCarts();
    }

    private void setUpViews() {
        fragmentMainCartBinding.swipeRefreshLayout.setOnRefreshListener(() -> cartViewModel.getCarts());

        cartItemModelArrayList = new ArrayList<>();
        cartRecyclerAdapter = new CartRecyclerAdapter(isCurrentLocaleAR, requireActivity(), cartItemModelArrayList, this);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity());
        fragmentMainCartBinding.cartRV.setLayoutManager(linearLayoutManager);
        fragmentMainCartBinding.cartRV.setAdapter(cartRecyclerAdapter);

        // Checking if the user has at least one address
        fragmentMainCartBinding.checkoutTV.setOnClickListener(v -> cartViewModel.getAddresses());
    }

    @Override
    protected void setUpObservers() {
        super.setUpObservers();

        final Observer<CartsResponseModel> cartsResponseModelObserver = cartsResponseModel -> {

            fragmentMainCartBinding.spinKit.setVisibility(View.GONE);

            fragmentMainCartBinding.emptyIV.setVisibility(cartsResponseModel.getCartItemModelList().isEmpty()?View.VISIBLE:View.GONE);

            cartItemModelArrayList.clear();
            cartItemModelArrayList.addAll(cartsResponseModel.getCartItemModelList());
            cartRecyclerAdapter.notifyDataSetChanged();

            int cartItemsViewsVisibility = cartsResponseModel.getTotalCount() == 0 ? View.GONE : View.VISIBLE;
            fragmentMainCartBinding.scrollView.setVisibility(cartItemsViewsVisibility);
            fragmentMainCartBinding.checkoutTV.setVisibility(cartItemsViewsVisibility);

            fragmentMainCartBinding.itemsCountTV.setText(cartsResponseModel.getTotalCount() + " " + getString(R.string.items));
            fragmentMainCartBinding.itemsPriceTV.setText("$" + String.format("%.1f", cartsResponseModel.getTotalPrice()));
            fragmentMainCartBinding.shippingPriceTV.setText("$" + String.valueOf(cartsResponseModel.getShipping()));
            fragmentMainCartBinding.totalPriceTV.setText("$" + String.format("%.1f", cartsResponseModel.getTotalPrice()));

            try {
                ((MainActivity) requireActivity()).onCartContentsChanged();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        };
        cartViewModel.getCartItemsLD().observe(this, cartsResponseModelObserver);

        final Observer<List<AddressModel>> addressesObserver = addressModels -> {
            if (addressModels.isEmpty())
                getBaseActivity().toastsHelper.showMessage(new ToastsHelper.ToastMessage(requireContext().getString(R.string.no_addresses_found), ToastsHelper.MESSAGE_TYPE_INFO));
            else
                openCheckoutActivity();
        };
        cartViewModel.getAddressesLD().observe(this, addressesObserver);

    }

    @Override
    public void onCartItemQuantityIncrement(CartItemModel cartItemModel) {
        cartViewModel.incrementCartItemQuantity(cartItemModel.getId());
    }

    @Override
    public void onCartItemQuantityDecrement(CartItemModel cartItemModel) {
        cartViewModel.decrementCartItemQuantity(cartItemModel.getId());
    }

    @Override
    public void onRemoveFromCartClicked(CartItemModel cartItemModel) {
        cartViewModel.removeCartItem(cartItemModel.getId());
    }

    private void openCheckoutActivity() {
        CheckoutActivity.startActivity(requireContext());
    }

}
