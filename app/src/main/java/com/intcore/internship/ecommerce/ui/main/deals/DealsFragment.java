package com.intcore.internship.ecommerce.ui.main.deals;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.intcore.internship.ecommerce.BR;
import com.intcore.internship.ecommerce.R;
import com.intcore.internship.ecommerce.data.remote.helperModels.deals.DealsPageResponseModel;
import com.intcore.internship.ecommerce.data.models.BrandModel;
import com.intcore.internship.ecommerce.data.models.CategoryModel;
import com.intcore.internship.ecommerce.data.models.ProductModel;
import com.intcore.internship.ecommerce.databinding.FragmentMainDealsBinding;
import com.intcore.internship.ecommerce.ui.baseClasses.BaseFragment;
import com.intcore.internship.ecommerce.ui.baseClasses.BaseViewModel;
import com.intcore.internship.ecommerce.ui.main.commons.BrandRecyclerAdapter;
import com.intcore.internship.ecommerce.ui.main.commons.CategoryRecyclerAdapter;
import com.intcore.internship.ecommerce.ui.main.commons.ProductRecyclerAdapter;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

public class DealsFragment extends BaseFragment<FragmentMainDealsBinding>
        implements ProductRecyclerAdapter.ClickListener , CategoryRecyclerAdapter.ClickListener , BrandRecyclerAdapter.ClickListener {

    public static final String TAG = DealsFragment.class.getSimpleName() ;

    private ProductRecyclerAdapter hotDealsAdapter ;
    private ArrayList<ProductModel> hotDealsModelArrayList;
    private CategoryRecyclerAdapter topCategoriesAdapter ;
    private ArrayList<CategoryModel> categoryModelArrayList;
    private BrandRecyclerAdapter brandsAdapter ;
    private ArrayList<BrandModel> brandModelArrayList ;



    public static DealsFragment newInstance() {
        Bundle args = new Bundle();
        DealsFragment fragment = new DealsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_deals;
    }

    @Override
    public BaseViewModel getViewModel() {
        dealsViewModel = ViewModelProviders.of(requireActivity(),
                getCompositionRoot().getViewModelProviderFactory()).get(DealsViewModel.class) ;
        return dealsViewModel;
    }

    private DealsViewModel dealsViewModel ;
    private FragmentMainDealsBinding fragmentMainDealsBinding ;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  super.onCreateView(inflater, container, savedInstanceState);
        fragmentMainDealsBinding = getViewDataBinding() ;
        setUpViews();
        dealsViewModel.getDealsPage();
        return view ;
    }

    private void setUpViews() {
        fragmentMainDealsBinding.swipeRefreshLayout.setOnRefreshListener(() -> dealsViewModel.getDealsPage());

        // hot deals section
        hotDealsModelArrayList = new ArrayList<>() ;
        hotDealsAdapter = new ProductRecyclerAdapter(hotDealsModelArrayList,this);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false);
        fragmentMainDealsBinding.todayHotDealsRV.setAdapter(hotDealsAdapter);
        fragmentMainDealsBinding.todayHotDealsRV.setLayoutManager(layoutManager1);

        // Top categories section
        categoryModelArrayList = new ArrayList<>() ;
        topCategoriesAdapter = new CategoryRecyclerAdapter(categoryModelArrayList,this);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false);
        fragmentMainDealsBinding.topCategoriesRV.setAdapter(topCategoriesAdapter);
        fragmentMainDealsBinding.topCategoriesRV.setLayoutManager(layoutManager2);

        // Top brands section
        brandModelArrayList = new ArrayList<>() ;
        brandsAdapter = new BrandRecyclerAdapter(brandModelArrayList,this);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false);
        fragmentMainDealsBinding.topBrandsRV.setAdapter(brandsAdapter);
        fragmentMainDealsBinding.topBrandsRV.setLayoutManager(layoutManager3);

    }

    @Override
    protected void setUpObservers() {
        super.setUpObservers();
        final Observer<DealsPageResponseModel> dealsPageResponseModelObserver = dealsPageResponseModel -> {
            if(fragmentMainDealsBinding.swipeRefreshLayout.isRefreshing())
                fragmentMainDealsBinding.swipeRefreshLayout.setRefreshing(false);
            handleAdaptersDataRefreshing(dealsPageResponseModel);
            handleSectionsVisibility();
        };
        dealsViewModel.getDealsPageResponseModelLD().observe(this,dealsPageResponseModelObserver);
    }

    private void handleAdaptersDataRefreshing(DealsPageResponseModel dealsPageResponseModel) {
        // hot deals section
        hotDealsModelArrayList.clear();
        hotDealsModelArrayList.addAll(dealsPageResponseModel.getHotDealsModelList()) ;
        hotDealsAdapter.notifyDataSetChanged();

        // Top categories section
        categoryModelArrayList.clear();
        categoryModelArrayList.addAll(dealsPageResponseModel.getTopCategoriesModelList()) ;
        topCategoriesAdapter.notifyDataSetChanged();

        // Top brands section
        brandModelArrayList.clear();
        brandModelArrayList.addAll(dealsPageResponseModel.getTopBrandsModelList()) ;
        brandsAdapter.notifyDataSetChanged();

    }

    private void handleSectionsVisibility() {
        // hot deals section
        int hotDealsVisibility = hotDealsModelArrayList.isEmpty()?View.GONE:View.VISIBLE ;
        fragmentMainDealsBinding.todayHotDealsTV.setVisibility(hotDealsVisibility);
        fragmentMainDealsBinding.todayHotDealsRV.setVisibility(hotDealsVisibility);

        // Top categories section
        int topCategoriesVisibility = categoryModelArrayList.isEmpty()?View.GONE:View.VISIBLE ;
        fragmentMainDealsBinding.topCategoriesTV.setVisibility(topCategoriesVisibility);
        fragmentMainDealsBinding.topCategoriesSeeMoreTV.setVisibility(topCategoriesVisibility);
        fragmentMainDealsBinding.topCategoriesRV.setVisibility(topCategoriesVisibility);

        // Top brands section
        int topBrandsVisibility = brandModelArrayList.isEmpty()?View.GONE:View.VISIBLE ;
        fragmentMainDealsBinding.topBrandsTV.setVisibility(topBrandsVisibility);
        fragmentMainDealsBinding.topBrandsSeeMoreTV.setVisibility(topBrandsVisibility);
        fragmentMainDealsBinding.topBrandsRV.setVisibility(topBrandsVisibility);
    }

    @Override
    public void onBrandClicked(BrandModel brandModel) {

    }

    @Override
    public void onCategoryClicked(CategoryModel categoryModel) {

    }

    @Override
    public void onProductImageClicked(ProductModel productModel) {

    }

    @Override
    public void onProductFavClicked(ProductModel productModel) {

    }
}
