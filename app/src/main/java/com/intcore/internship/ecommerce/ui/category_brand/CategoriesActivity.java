package com.intcore.internship.ecommerce.ui.category_brand;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.intcore.internship.ecommerce.R;
import com.intcore.internship.ecommerce.data.models.CategoryModel;
import com.intcore.internship.ecommerce.data.sharedPreferences.PreferenceHelper;
import com.intcore.internship.ecommerce.databinding.ActivityCategoriesBinding;
import com.intcore.internship.ecommerce.ui.baseClasses.BaseActivity;
import com.intcore.internship.ecommerce.ui.baseClasses.BaseViewModel;
import com.intcore.internship.ecommerce.ui.commonClasses.CategoryRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class CategoriesActivity extends BaseActivity<ActivityCategoriesBinding> implements CategoryRecyclerAdapter.ClickListener {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, CategoriesActivity.class);
        context.startActivity(intent);
    }

    private CategoriesViewModel categoriesViewModel;
    private ActivityCategoriesBinding binding;

    private CategoryRecyclerAdapter categoryRecyclerAdapter;
    private ArrayList<CategoryModel> categoryModelList;

    private boolean isCurrentLocaleAR;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_categories;
    }

    @Override
    public BaseViewModel getViewModel() {
        categoriesViewModel = ViewModelProviders.of(this,
                getCompositionRoot().getViewModelProviderFactory()).get(CategoriesViewModel.class);
        return categoriesViewModel;
    }

    @Nullable
    @Override
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewDataBinding();
        isCurrentLocaleAR = categoriesViewModel.getSavedLocale().equals(PreferenceHelper.LOCALE_ARABIC);
        setUpToolbar();
        setupViews();
        categoriesViewModel.getCategories();
    }

    private void setUpToolbar() {
        setSupportActionBar((Toolbar) binding.toolbar);
        binding.toolbar.findViewById(R.id.toolbarBackBtn).setVisibility(View.VISIBLE);
        binding.toolbar.findViewById(R.id.toolbarBackBtn).setOnClickListener(v -> onBackPressed());
        ((TextView) binding.toolbar.findViewById(R.id.toolbarTitleTV)).setText(R.string.categories);
    }

    @Override
    protected void setUpObservers() {
        super.setUpObservers();

        final Observer<List<CategoryModel>> categoryListObserver = categoryModelList -> {
            CategoriesActivity.this.categoryModelList.clear();
            CategoriesActivity.this.categoryModelList.addAll(categoryModelList);
            categoryRecyclerAdapter.notifyDataSetChanged();
        };
        categoriesViewModel.getCategoriesListLD().observe(this, categoryListObserver);

    }

    private void setupViews() {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.categoriesRV.setLayoutManager(linearLayoutManager);
        categoryModelList = new ArrayList<>();
        categoryRecyclerAdapter = new CategoryRecyclerAdapter(isCurrentLocaleAR, this, CategoryRecyclerAdapter.MODE_VERTICAL, categoryModelList, this);
        binding.categoriesRV.setAdapter(categoryRecyclerAdapter);
    }

    @Override
    public void onCategoryClicked(CategoryModel categoryModel) {
        CategoryBrandActivity.startActivity(this, categoryModel);
    }
}
