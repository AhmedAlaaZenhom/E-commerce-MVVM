package com.intcore.internship.ecommerce.ui.main.wishlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.intcore.internship.ecommerce.BR;
import com.intcore.internship.ecommerce.R;
import com.intcore.internship.ecommerce.data.models.ProductModel;
import com.intcore.internship.ecommerce.data.sharedPreferences.PreferenceHelper;
import com.intcore.internship.ecommerce.databinding.FragmentMainWishlistBinding;
import com.intcore.internship.ecommerce.ui.baseClasses.BaseFragment;
import com.intcore.internship.ecommerce.ui.baseClasses.BaseViewModel;
import com.intcore.internship.ecommerce.ui.product.ProductScreenActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class WishListFragment extends BaseFragment<FragmentMainWishlistBinding> implements WishListRecyclerAdapter.ClickListener {

    public static final String TAG = WishListFragment.class.getSimpleName() ;

    public static WishListFragment newInstance() {
        Bundle args = new Bundle();
        WishListFragment fragment = new WishListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private WishListViewModel wishListViewModel ;
    private FragmentMainWishlistBinding fragmentMainWishlistBinding ;

    private WishListRecyclerAdapter wishListRecyclerAdapter;
    private ArrayList<ProductModel> productModelArrayList;
    private boolean isCurrentLocaleAR;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_wishlist;
    }

    @Override
    public BaseViewModel getViewModel() {
        wishListViewModel = ViewModelProviders.of(this,
                getCompositionRoot().getViewModelProviderFactory()).get(WishListViewModel.class) ;
        return wishListViewModel;
    }

    @Nullable
    @Override
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return null;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  super.onCreateView(inflater, container, savedInstanceState);
        fragmentMainWishlistBinding = getViewDataBinding() ;
        isCurrentLocaleAR = wishListViewModel.getSavedLocale().equals(PreferenceHelper.LOCALE_ARABIC);
        setUpViews();
        return view;
    }

    @Override
    protected void setUpObservers() {
        super.setUpObservers();
        final Observer<List<ProductModel>> productModelListObserver = productModels -> {
            fragmentMainWishlistBinding.spinKit.setVisibility(View.GONE);
            fragmentMainWishlistBinding.emptyIV.setVisibility(productModels.isEmpty() ? View.VISIBLE : View.GONE);
            productModelArrayList.clear();
            productModelArrayList.addAll(productModels);
            wishListRecyclerAdapter.notifyDataSetChanged();
        };
        wishListViewModel.getProductModelListLD().observe(this, productModelListObserver);
    }

    private void setUpViews(){
        productModelArrayList = new ArrayList<>();
        wishListRecyclerAdapter = new WishListRecyclerAdapter(isCurrentLocaleAR, requireActivity(), productModelArrayList,this);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext()) ;
        fragmentMainWishlistBinding.wishListRV.setLayoutManager(linearLayoutManager);
        fragmentMainWishlistBinding.wishListRV.setAdapter(wishListRecyclerAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        wishListViewModel.getWishList();
    }

    @Override
    public void onItemClicked(ProductModel productModel) {
        ProductScreenActivity.startActivityForResult(requireActivity(),productModel.getId());
    }

    @Override
    public void onRemoveItemClicked(ProductModel productModel) {
        wishListViewModel.toggleFavState(productModel.getId());
    }
}
