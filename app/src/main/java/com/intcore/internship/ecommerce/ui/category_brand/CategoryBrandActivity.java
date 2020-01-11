package com.intcore.internship.ecommerce.ui.category_brand;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.intcore.internship.ecommerce.BR;
import com.intcore.internship.ecommerce.R;
import com.intcore.internship.ecommerce.data.models.BrandModel;
import com.intcore.internship.ecommerce.data.models.CategoryModel;
import com.intcore.internship.ecommerce.data.models.ProductModel;
import com.intcore.internship.ecommerce.data.remote.helperModels.filter.FilterDataResponseModel;
import com.intcore.internship.ecommerce.data.sharedPreferences.PreferenceHelper;
import com.intcore.internship.ecommerce.databinding.ActivityCategoryBrandBinding;
import com.intcore.internship.ecommerce.ui.baseClasses.BaseActivity;
import com.intcore.internship.ecommerce.ui.baseClasses.BaseViewModel;
import com.intcore.internship.ecommerce.ui.commonClasses.PicassoHelper;
import com.intcore.internship.ecommerce.ui.commonClasses.ProductRecyclerAdapter;
import com.intcore.internship.ecommerce.ui.commonClasses.ToastsHelper;
import com.intcore.internship.ecommerce.ui.product.ProductScreenActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class CategoryBrandActivity extends BaseActivity<ActivityCategoryBrandBinding>
        implements ProductRecyclerAdapter.ClickListener, SortByDialog.ClickListener, FilterDialog.ClickListener {

    private static final int ACTIVITY_MODE_CATEGORY = 1, ACTIVITY_MODE_BRAND = 2;
    private static final String ACTIVITY_MODE = "ACTIVITY_MODE";
    private static final String DATA = "DATA";

    public static void startActivity(Context context, CategoryModel categoryModel) {
        Intent intent = new Intent(context, CategoryBrandActivity.class);
        intent.putExtra(ACTIVITY_MODE, ACTIVITY_MODE_CATEGORY);
        intent.putExtra(DATA, categoryModel);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, BrandModel brandModel) {
        Intent intent = new Intent(context, CategoryBrandActivity.class);
        intent.putExtra(ACTIVITY_MODE, ACTIVITY_MODE_BRAND);
        intent.putExtra(DATA, brandModel);
        context.startActivity(intent);
    }

    private CategoryBrandViewModel categoryBrandViewModel;
    private ActivityCategoryBrandBinding binding;

    private int activityMode;
    private CategoryModel currentCategoryModel;
    private BrandModel currentBrandModel;

    private ProductRecyclerAdapter productRecyclerAdapter;
    private ArrayList<ProductModel> productModelList;

    private boolean isCurrentLocaleAR;

    private FilterDialog filterDialog;

    @Nullable
    private Integer categoryID;
    @Nullable
    private Integer brandID;
    @Nullable
    private String keyword;
    @Nullable
    private Integer subCategoryID;
    @Nullable
    private Integer maxPrice;
    @Nullable
    private Integer minPrice;
    @Nullable
    private Integer sortBy;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_category_brand;
    }

    @Override
    public BaseViewModel getViewModel() {
        categoryBrandViewModel = ViewModelProviders.of(this,
                getCompositionRoot().getViewModelProviderFactory()).get(CategoryBrandViewModel.class);
        return categoryBrandViewModel;
    }

    @Nullable
    @Override
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return binding.swipeRefreshLayout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewDataBinding();
        isCurrentLocaleAR = categoryBrandViewModel.getSavedLocale().equals(PreferenceHelper.LOCALE_ARABIC);
        getDataFromIntent();
        setUpToolBar();
        setUpViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getProducts();
        if (activityMode == ACTIVITY_MODE_CATEGORY && filterDialog == null) {
            categoryBrandViewModel.getFilterData(currentCategoryModel.getId());
        }
    }

    private void getDataFromIntent() {
        activityMode = getIntent().getIntExtra(ACTIVITY_MODE, ACTIVITY_MODE_CATEGORY);
        try {
            if (activityMode == ACTIVITY_MODE_CATEGORY) {
                currentCategoryModel = (CategoryModel) getIntent().getSerializableExtra(DATA);
                categoryID = Objects.requireNonNull(currentCategoryModel).getId();
            } else {
                currentBrandModel = (BrandModel) getIntent().getSerializableExtra(DATA);
                brandID = Objects.requireNonNull(currentBrandModel).getId();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            toastsHelper.showUnknownError();
            finish();
        }
    }

    @Override
    protected void setUpObservers() {
        super.setUpObservers();

        final Observer<List<ProductModel>> productModelListObserver = productModelList -> {
            CategoryBrandActivity.this.productModelList.clear();
            CategoryBrandActivity.this.productModelList.addAll(productModelList);
            productRecyclerAdapter.notifyDataSetChanged();
        };
        categoryBrandViewModel.getProductModelListLD().observe(this, productModelListObserver);

        final Observer<FilterDataResponseModel> filterDataResponseModelObserver = filterDataResponseModel -> {
            if (filterDataResponseModel.getMinPrice() == 0 && filterDataResponseModel.getMaxPrice() == 0)
                return;
            filterDialog = new FilterDialog(CategoryBrandActivity.this,
                    isCurrentLocaleAR,
                    (int) filterDataResponseModel.getMinPrice(),
                    (int) filterDataResponseModel.getMaxPrice(),
                    filterDataResponseModel.getSubCategoryModels(),
                    filterDataResponseModel.getBrandModelList(),
                    CategoryBrandActivity.this);
        };
        categoryBrandViewModel.getFilterDataResponseModelLD().observe(this, filterDataResponseModelObserver);
    }

    private void setUpToolBar() {
        setSupportActionBar((Toolbar) binding.toolbar);
        binding.toolbar.findViewById(R.id.toolbarBackBtn).setVisibility(View.VISIBLE);
        binding.toolbar.findViewById(R.id.toolbarBackBtn).setOnClickListener(v -> onBackPressed());

        if (activityMode == ACTIVITY_MODE_CATEGORY)
            ((TextView) binding.toolbar.findViewById(R.id.toolbarTitleTV)).setText(isCurrentLocaleAR ? currentCategoryModel.getNameAR() : currentCategoryModel.getNameEN());
        else
            ((TextView) binding.toolbar.findViewById(R.id.toolbarTitleTV)).setText(isCurrentLocaleAR ? currentBrandModel.getNameAR() : currentBrandModel.getNameEN());
    }

    private void setUpViews() {
        binding.swipeRefreshLayout.setOnRefreshListener(() -> categoryBrandViewModel.getProducts(keyword, categoryID, subCategoryID, brandID, maxPrice, minPrice, sortBy));

        if(activityMode==ACTIVITY_MODE_CATEGORY){
            binding.sortFilterView.setVisibility(View.VISIBLE);
            binding.sortByTV.setVisibility(View.VISIBLE);
            binding.filterTV.setVisibility(View.VISIBLE);

            final SortByDialog sortByDialog = new SortByDialog(this, this);
            binding.sortByTV.setOnClickListener(v -> sortByDialog.show());

            binding.filterTV.setOnClickListener(v -> {
                if(filterDialog==null)
                    toastsHelper.showMessage(new ToastsHelper.ToastMessage(getString(R.string.no_filter_data_available), ToastsHelper.MESSAGE_TYPE_WARNING));
                else
                    filterDialog.show();
            });
        }

        PicassoHelper.loadImageWithCache(
                PicassoHelper.STORAGE_BASE_URL + (activityMode == ACTIVITY_MODE_CATEGORY ? currentCategoryModel.getImageURL() : currentBrandModel.getImageUrl()),
                binding.categoryBrandIV,
                PicassoHelper.CENTER_INSIDE,
                null,
                null);

        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        binding.productsRV.setLayoutManager(gridLayoutManager);
        productModelList = new ArrayList<>();
        productRecyclerAdapter = new ProductRecyclerAdapter(isCurrentLocaleAR, this, ProductRecyclerAdapter.MODE_GRID, productModelList, this);
        binding.productsRV.setAdapter(productRecyclerAdapter);
        final int spacing = productRecyclerAdapter.getGridModeSpacing();
        binding.productsRV.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.set(spacing, spacing, spacing, spacing);
            }
        });
    }

    private void getProducts() {
        categoryBrandViewModel.getProducts(keyword, categoryID, subCategoryID, brandID, maxPrice, minPrice, sortBy);
    }

    @Override
    public void onProductImageClicked(ProductModel productModel) {
        ProductScreenActivity.startActivityForResult(this, productModel.getId());
    }

    @Override
    public void onProductFavClicked(ProductModel productModel) {
        categoryBrandViewModel.toggleFavState(productModel.getId());
    }

    @Override
    public void onSortOptionClicked(int option) {
        sortBy = option;
        getProducts();
    }

    @Override
    public void onFilterClicked(int minPrice, int maxPrice, Integer subCategoryID, Integer brandID) {
        CategoryBrandActivity.this.minPrice = minPrice ;
        CategoryBrandActivity.this.maxPrice = maxPrice ;
        CategoryBrandActivity.this.subCategoryID = subCategoryID ;
        CategoryBrandActivity.this.brandID = brandID ;
        getProducts();
    }
}
