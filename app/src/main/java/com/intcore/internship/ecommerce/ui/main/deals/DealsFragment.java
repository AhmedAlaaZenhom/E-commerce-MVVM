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
import com.intcore.internship.ecommerce.data.sharedPreferences.PreferenceHelper;
import com.intcore.internship.ecommerce.databinding.FragmentMainDealsBinding;
import com.intcore.internship.ecommerce.ui.baseClasses.BaseFragment;
import com.intcore.internship.ecommerce.ui.baseClasses.BaseViewModel;
import com.intcore.internship.ecommerce.ui.category_brand.CategoriesActivity;
import com.intcore.internship.ecommerce.ui.category_brand.CategoryBrandActivity;
import com.intcore.internship.ecommerce.ui.commonClasses.CategoryRecyclerAdapter;
import com.intcore.internship.ecommerce.ui.commonClasses.ProductRecyclerAdapter;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class DealsFragment extends BaseFragment<FragmentMainDealsBinding>
        implements ProductRecyclerAdapter.ClickListener , CategoryRecyclerAdapter.ClickListener , BrandRecyclerAdapter.ClickListener {

    public static final String TAG = DealsFragment.class.getSimpleName() ;

    private ProductRecyclerAdapter hotDealsAdapter ;
    private ArrayList<ProductModel> hotDealsModelArrayList;
    private CategoryRecyclerAdapter topCategoriesAdapter ;
    private ArrayList<CategoryModel> categoryModelArrayList;
    private BrandRecyclerAdapter brandsAdapter ;
    private ArrayList<BrandModel> brandModelArrayList ;

    private boolean isCurrentLocaleAR;



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
        dealsViewModel = ViewModelProviders.of(this,
                getCompositionRoot().getViewModelProviderFactory()).get(DealsViewModel.class) ;
        return dealsViewModel;
    }

    @Nullable
    @Override
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return fragmentMainDealsBinding.swipeRefreshLayout;
    }

    private DealsViewModel dealsViewModel ;
    private FragmentMainDealsBinding fragmentMainDealsBinding ;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  super.onCreateView(inflater, container, savedInstanceState);
        fragmentMainDealsBinding = getViewDataBinding() ;
        isCurrentLocaleAR = dealsViewModel.getSavedLocale().equals(PreferenceHelper.LOCALE_ARABIC);

        setUpViews();
        dealsViewModel.getDealsPage();
        return view ;
    }

    private void setUpViews() {
        fragmentMainDealsBinding.swipeRefreshLayout.setOnRefreshListener(() -> dealsViewModel.getDealsPage());

        // hot deals section
        hotDealsModelArrayList = new ArrayList<>() ;
        hotDealsAdapter = new ProductRecyclerAdapter(isCurrentLocaleAR, requireActivity(),ProductRecyclerAdapter.MODE_HORIZONTAL,hotDealsModelArrayList,this);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false);
        fragmentMainDealsBinding.todayHotDealsRV.setAdapter(hotDealsAdapter);
        fragmentMainDealsBinding.todayHotDealsRV.setLayoutManager(layoutManager1);

        // Top categories section
        categoryModelArrayList = new ArrayList<>() ;
        topCategoriesAdapter = new CategoryRecyclerAdapter(isCurrentLocaleAR, requireActivity(),CategoryRecyclerAdapter.MODE_HORIZONTAL,categoryModelArrayList,this);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false);
        fragmentMainDealsBinding.topCategoriesRV.setAdapter(topCategoriesAdapter);
        fragmentMainDealsBinding.topCategoriesRV.setLayoutManager(layoutManager2);
        fragmentMainDealsBinding.topCategoriesSeeMoreTV.setOnClickListener(v -> CategoriesActivity.startActivity(requireContext()));


        // Top brands section
        brandModelArrayList = new ArrayList<>() ;
        brandsAdapter = new BrandRecyclerAdapter(requireActivity(),brandModelArrayList,this);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false);
        fragmentMainDealsBinding.topBrandsRV.setAdapter(brandsAdapter);
        fragmentMainDealsBinding.topBrandsRV.setLayoutManager(layoutManager3);

    }

    @Override
    protected void setUpObservers() {
        super.setUpObservers();
        final Observer<DealsPageResponseModel> dealsPageResponseModelObserver = dealsPageResponseModel -> {
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
        fragmentMainDealsBinding.todayHotDealsSeeMoreTV.setVisibility(View.GONE);
        fragmentMainDealsBinding.todayHotDealsRV.setVisibility(hotDealsVisibility);

        // Top categories section
        int topCategoriesVisibility = categoryModelArrayList.isEmpty()?View.GONE:View.VISIBLE ;
        fragmentMainDealsBinding.topCategoriesTV.setVisibility(topCategoriesVisibility);
        fragmentMainDealsBinding.topCategoriesSeeMoreTV.setVisibility(topCategoriesVisibility);
        fragmentMainDealsBinding.topCategoriesRV.setVisibility(topCategoriesVisibility);

        // Top brands section
        int topBrandsVisibility = brandModelArrayList.isEmpty()?View.GONE:View.VISIBLE ;
        fragmentMainDealsBinding.topBrandsTV.setVisibility(topBrandsVisibility);
        fragmentMainDealsBinding.topBrandsSeeMoreTV.setVisibility(View.GONE);
        fragmentMainDealsBinding.topBrandsRV.setVisibility(topBrandsVisibility);
    }

    @Override
    public void onBrandClicked(BrandModel brandModel) {
        CategoryBrandActivity.startActivity(requireContext(),brandModel);
    }

    @Override
    public void onCategoryClicked(CategoryModel categoryModel) {
        CategoryBrandActivity.startActivity(requireContext(),categoryModel);
    }

    @Override
    public void onProductImageClicked(ProductModel productModel) {

    }

    @Override
    public void onProductFavClicked(ProductModel productModel) {

    }
}
