package com.intcore.internship.ecommerce.ui.main.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.intcore.internship.ecommerce.BR;
import com.intcore.internship.ecommerce.R;
import com.intcore.internship.ecommerce.data.remote.helperModels.home.HomeResponseModel;
import com.intcore.internship.ecommerce.data.models.CategoryModel;
import com.intcore.internship.ecommerce.data.models.ProductModel;
import com.intcore.internship.ecommerce.data.sharedPreferences.PreferenceHelper;
import com.intcore.internship.ecommerce.databinding.FragmentMainHomeBinding;
import com.intcore.internship.ecommerce.ui.baseClasses.BaseFragment;
import com.intcore.internship.ecommerce.ui.baseClasses.BaseViewModel;
import com.intcore.internship.ecommerce.ui.category_brand.CategoriesActivity;
import com.intcore.internship.ecommerce.ui.category_brand.CategoryBrandActivity;
import com.intcore.internship.ecommerce.ui.commonClasses.CategoryRecyclerAdapter;
import com.intcore.internship.ecommerce.ui.commonClasses.ProductRecyclerAdapter;
import com.intcore.internship.ecommerce.ui.product.ProductScreenActivity;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class HomeFragment extends BaseFragment <FragmentMainHomeBinding>
        implements ProductRecyclerAdapter.ClickListener , CategoryRecyclerAdapter.ClickListener {

    public static final String TAG = HomeFragment.class.getSimpleName() ;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private HomeViewModel homeViewModel ;
    private FragmentMainHomeBinding fragmentMainHomeBinding ;

    private ProductRecyclerAdapter newArrivalsAdapter , bestSellerAdapter , hotDealsAdapter ;
    private ArrayList<ProductModel> newArrivalsArrayList , bestSellerArrayList , hotDealsArrayList ;
    private CategoryRecyclerAdapter categoryRecyclerAdapter ;
    private ArrayList<CategoryModel> categoryModelArrayList ;

    private boolean isCurrentLocaleAR;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_home;
    }

    @Override
    public BaseViewModel getViewModel() {
        homeViewModel = ViewModelProviders.of(this,
                getCompositionRoot().getViewModelProviderFactory()).get(HomeViewModel.class) ;
        return homeViewModel;
    }

    @Nullable
    @Override
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return fragmentMainHomeBinding.swipeRefreshLayout;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  super.onCreateView(inflater, container, savedInstanceState);
        fragmentMainHomeBinding = getViewDataBinding() ;
        isCurrentLocaleAR = homeViewModel.getSavedLocale().equals(PreferenceHelper.LOCALE_ARABIC);
        setUpViews();
        return view ;
    }

    @Override
    public void onStart() {
        super.onStart();
        homeViewModel.getHome();
    }

    private void setUpViews() {
        fragmentMainHomeBinding.swipeRefreshLayout.setOnRefreshListener(() -> homeViewModel.getHome());

        // Top categories section
        categoryModelArrayList = new ArrayList<>() ;
        categoryRecyclerAdapter = new CategoryRecyclerAdapter(isCurrentLocaleAR, requireActivity(),CategoryRecyclerAdapter.MODE_HORIZONTAL,categoryModelArrayList,this);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false);
        fragmentMainHomeBinding.topCategoriesRV.setAdapter(categoryRecyclerAdapter);
        fragmentMainHomeBinding.topCategoriesRV.setLayoutManager(layoutManager3);
        fragmentMainHomeBinding.topCategoriesSeeMoreTV.setOnClickListener(v -> CategoriesActivity.startActivity(requireContext()));

        // New arrivals section
        newArrivalsArrayList = new ArrayList<>() ;
        newArrivalsAdapter = new ProductRecyclerAdapter(isCurrentLocaleAR, requireActivity(),ProductRecyclerAdapter.MODE_HORIZONTAL,newArrivalsArrayList,this);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false);
        fragmentMainHomeBinding.newArrivalsRV.setAdapter(newArrivalsAdapter);
        fragmentMainHomeBinding.newArrivalsRV.setLayoutManager(layoutManager1);

        // Best seller section
        bestSellerArrayList = new ArrayList<>() ;
        bestSellerAdapter = new ProductRecyclerAdapter(isCurrentLocaleAR, requireActivity(),ProductRecyclerAdapter.MODE_HORIZONTAL,bestSellerArrayList,this);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false);
        fragmentMainHomeBinding.bestSellerRV.setAdapter(bestSellerAdapter);
        fragmentMainHomeBinding.bestSellerRV.setLayoutManager(layoutManager2);

        // Hot deals section
        hotDealsArrayList = new ArrayList<>() ;
        hotDealsAdapter = new ProductRecyclerAdapter(isCurrentLocaleAR, requireActivity(),ProductRecyclerAdapter.MODE_HORIZONTAL,hotDealsArrayList,this);
        LinearLayoutManager layoutManager4 = new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false);
        fragmentMainHomeBinding.hotDealsRV.setAdapter(hotDealsAdapter);
        fragmentMainHomeBinding.hotDealsRV.setLayoutManager(layoutManager4);

    }

    @Override
    protected void setUpObservers(){
        super.setUpObservers();
        final Observer<HomeResponseModel> homeResponseModelObserver = homeResponseModel -> {
            handleAdaptersDataRefreshing(homeResponseModel);
            handleSectionsVisibility();
        };
        homeViewModel.getHomeResponseModelLD().observe(this,homeResponseModelObserver);
    }

    private void handleAdaptersDataRefreshing(HomeResponseModel homeResponseModel){
        // new arrivals refreshing
        newArrivalsArrayList.clear();
        newArrivalsArrayList.addAll(homeResponseModel.getNewArrivalModelList()) ;
        newArrivalsAdapter.notifyDataSetChanged();

        // Best seller refreshing
        bestSellerArrayList.clear();
        bestSellerArrayList.addAll(homeResponseModel.getBestSellerModelList()) ;
        bestSellerAdapter.notifyDataSetChanged();

        // Top categories refreshing
        categoryModelArrayList.clear();
        categoryModelArrayList.addAll(homeResponseModel.getTopCategoryModelList()) ;
        categoryRecyclerAdapter.notifyDataSetChanged();

        // Hot deals refreshing
        hotDealsArrayList.clear();
        hotDealsArrayList.addAll(homeResponseModel.getHotDealModelList()) ;
        hotDealsAdapter.notifyDataSetChanged();
    }

    private void handleSectionsVisibility() {
        // Top categories refreshing
        int topCategoriesVisibility = categoryModelArrayList.isEmpty() ? View.GONE : View.VISIBLE;
        fragmentMainHomeBinding.topCategoriesTV.setVisibility(topCategoriesVisibility);
        fragmentMainHomeBinding.topCategoriesSeeMoreTV.setVisibility(topCategoriesVisibility);
        fragmentMainHomeBinding.topCategoriesRV.setVisibility(topCategoriesVisibility);

        // new arrivals refreshing
        int newArrivalsVisibility = newArrivalsArrayList.isEmpty() ? View.GONE : View.VISIBLE;
        fragmentMainHomeBinding.newArrivalsTV.setVisibility(newArrivalsVisibility);
        fragmentMainHomeBinding.newArrivalsSeeMoreTV.setVisibility(View.GONE);
        fragmentMainHomeBinding.newArrivalsRV.setVisibility(newArrivalsVisibility);

        // Best seller refreshing
        int bestSellerVisibility = bestSellerArrayList.isEmpty() ? View.GONE : View.VISIBLE;
        fragmentMainHomeBinding.bestSellerTV.setVisibility(bestSellerVisibility);
        fragmentMainHomeBinding.bestSellerSeeMoreTV.setVisibility(View.GONE);
        fragmentMainHomeBinding.bestSellerRV.setVisibility(bestSellerVisibility);

        // Hot deals refreshing
        int hotDealsVisibility = hotDealsArrayList.isEmpty() ? View.GONE : View.VISIBLE;
        fragmentMainHomeBinding.hotDealsTV.setVisibility(hotDealsVisibility);
        fragmentMainHomeBinding.hotDealsSeeMoreTV.setVisibility(View.GONE);
        fragmentMainHomeBinding.hotDealsRV.setVisibility(hotDealsVisibility);
    }

    @Override
    public void onProductImageClicked(ProductModel productModel) {
        ProductScreenActivity.startActivityForResult(requireActivity(),productModel.getId());
    }

    @Override
    public void onProductFavClicked(ProductModel productModel) {
        homeViewModel.toggleFavState(productModel.getId());
    }

    @Override
    public void onCategoryClicked(CategoryModel categoryModel) {
        CategoryBrandActivity.startActivity(requireContext(),categoryModel);
    }

}
