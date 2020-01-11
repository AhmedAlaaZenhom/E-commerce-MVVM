package com.intcore.internship.ecommerce.ui.product;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.intcore.internship.ecommerce.BR;
import com.intcore.internship.ecommerce.R;
import com.intcore.internship.ecommerce.Utils.CommonUtils;
import com.intcore.internship.ecommerce.data.models.CartItemModel;
import com.intcore.internship.ecommerce.data.models.ImageModel;
import com.intcore.internship.ecommerce.data.models.ProductModel;
import com.intcore.internship.ecommerce.data.sharedPreferences.PreferenceHelper;
import com.intcore.internship.ecommerce.databinding.ActivityProductScreenBinding;
import com.intcore.internship.ecommerce.ui.baseClasses.BaseActivity;
import com.intcore.internship.ecommerce.ui.baseClasses.BaseViewModel;
import com.intcore.internship.ecommerce.ui.commonClasses.ActivitiesRequestCodes;
import com.intcore.internship.ecommerce.ui.commonClasses.PicassoHelper;
import com.intcore.internship.ecommerce.ui.commonClasses.Utils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class ProductScreenActivity extends BaseActivity<ActivityProductScreenBinding>
        implements AddCartCompletionDialog.ClickListener {

    private static final String PRODUCT_ID = "PRODUCT_ID";

    public static void startActivityForResult(Activity activity, int productID) {
        Intent intent = new Intent(activity, ProductScreenActivity.class);
        intent.putExtra(PRODUCT_ID, productID);
        activity.startActivityForResult(intent, ActivitiesRequestCodes.PRODUCT_SCREEN_REQUEST_CODE);
    }

    // ViewModel
    private ProductScreenViewModel productScreenViewModel;
    private ActivityProductScreenBinding activityProductScreenBinding;

    private ProductModel currentProductModel;
    private ArrayList<ImageView> imageViewArrayList;

    private boolean isCurrentLocaleAR;

    // Main views
    private TextView toolbarTitleTV, toolbarCartCountTV;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_product_screen;
    }

    @Override
    public BaseViewModel getViewModel() {
        productScreenViewModel = ViewModelProviders.of(this,
                getCompositionRoot().getViewModelProviderFactory()).get(ProductScreenViewModel.class);
        return productScreenViewModel;
    }

    @Nullable
    @Override
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProductScreenBinding = getViewDataBinding();
        isCurrentLocaleAR = productScreenViewModel.getSavedLocale().equals(PreferenceHelper.LOCALE_ARABIC);
        setUpToolBar();
        setUpViews();

        // Objects
        final int currentProductId = getIntent().getIntExtra(PRODUCT_ID, -1);
        productScreenViewModel.getProduct(currentProductId);

        productScreenViewModel.getCarts();
    }

    private void setUpToolBar() {
        setSupportActionBar((Toolbar) activityProductScreenBinding.toolbar);

        toolbarTitleTV = activityProductScreenBinding.toolbar.findViewById(R.id.toolbarTitleTV);
        toolbarCartCountTV = activityProductScreenBinding.toolbar.findViewById(R.id.toolbarCartCountTV);

        activityProductScreenBinding.toolbar.findViewById(R.id.toolbarBackBtn).setVisibility(View.VISIBLE);
        activityProductScreenBinding.toolbar.findViewById(R.id.toolbarBackBtn).setOnClickListener(v -> onBackPressed());

        activityProductScreenBinding.toolbar.findViewById(R.id.toolbarCartIV).setVisibility(View.VISIBLE);
        activityProductScreenBinding.toolbar.findViewById(R.id.toolbarCartIV).setOnClickListener(v -> {
            setResult(RESULT_OK);
            onBackPressed();
        });

    }

    private void setUpViews() {
        activityProductScreenBinding.favIV.setOnClickListener(v -> {
            if (currentProductModel != null)
                productScreenViewModel.toggleFavState(currentProductModel.getId());
        });

        activityProductScreenBinding.addToCartTV.setOnClickListener(v -> {
            if (currentProductModel != null)
                new AddCartCompletionDialog(this,
                        currentProductModel.getSizeModelList(), currentProductModel.getColorModelList(),
                        this).show();
        });
    }

    @Override
    protected void setUpObservers() {
        super.setUpObservers();
        final Observer<ProductModel> productModelObserver = productModel -> {
            currentProductModel = productModel;
            bindProductImages();
            bindProductData();
        };
        productScreenViewModel.getProductModelLD().observe(this, productModelObserver);

        final Observer<List<CartItemModel>> cartItemsObserver = cartItemModels -> {
            toolbarCartCountTV.setText(String.valueOf(cartItemModels.size()));
            toolbarCartCountTV.setVisibility(cartItemModels.isEmpty() ? View.GONE : View.VISIBLE);
        };
        productScreenViewModel.getCartItemsLD().observe(this, cartItemsObserver);
    }

    private void bindProductData() {
        toolbarTitleTV.setText(isCurrentLocaleAR ? currentProductModel.getNameAR() : currentProductModel.getNameEN());
        activityProductScreenBinding.favIV.setImageResource(currentProductModel.isFav() ? R.drawable.heart_filled : R.drawable.heart_empty);
        activityProductScreenBinding.productNameTV.setText(isCurrentLocaleAR ? currentProductModel.getNameAR() : currentProductModel.getNameEN());
        activityProductScreenBinding.productPriceTV.setText("$" + String.format("%.1f", currentProductModel.getPrice()));
        activityProductScreenBinding.productRatingBar.setRating(currentProductModel.getTotalRate());
        activityProductScreenBinding.productDescriptionTV.setText(isCurrentLocaleAR ? currentProductModel.getDescriptionAR() : currentProductModel.getDescriptionEN());
        activityProductScreenBinding.productCategoryTV.setText(getString(R.string.category) + " " + (isCurrentLocaleAR ? currentProductModel.getSubCategoryModel().getNameAR() : currentProductModel.getSubCategoryModel().getNameEN()));
        if (currentProductModel.getBrandModel() != null)
            activityProductScreenBinding.productBrandTV.setText(getString(R.string.brand) + " " + (isCurrentLocaleAR ? currentProductModel.getBrandModel().getNameAR() : currentProductModel.getBrandModel().getNameEN()));
        else
            activityProductScreenBinding.productBrandTV.setVisibility(View.GONE);
    }

    private void bindProductImages() {
        List<ImageModel> imageModelArrayList = currentProductModel.getImageModelList();
        if (imageModelArrayList.isEmpty())
            return;
        // Check if images has been initialized before, therefore cancel binding them
        if (imageViewArrayList != null)
            return;
        imageViewArrayList = new ArrayList<>();

        PicassoHelper.loadImageWithCache(
                PicassoHelper.STORAGE_BASE_URL + imageModelArrayList.get(0).getImage(),
                activityProductScreenBinding.productIV,
                PicassoHelper.CENTER_INSIDE,
                null,
                null);

        int size = (int) (Utils.getDisplayMetrics(this).widthPixels * 0.2);
        int pxMargin = (int) Utils.convertDpToPixel(this, 8);
        for (int i = 0; i < imageModelArrayList.size(); i++) {
            int finalI = i;
            final ImageModel imageModel = imageModelArrayList.get(i);
            final ImageView imageView = new ImageView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(size, size);
            layoutParams.setMarginStart(pxMargin);
            imageView.setLayoutParams(layoutParams);
            imageView.setOnClickListener(v -> onSmallImageClicked(finalI));
            activityProductScreenBinding.productImagesLL.addView(imageView);
            imageViewArrayList.add(imageView);

            PicassoHelper.loadImageWithCache(
                    PicassoHelper.STORAGE_BASE_URL + imageModel.getImage(),
                    imageView,
                    PicassoHelper.CENTER_INSIDE,
                    null,
                    null);
        }
    }

    private void onSmallImageClicked(int order) {
        List<ImageModel> imageModelArrayList = currentProductModel.getImageModelList();

        PicassoHelper.loadImageWithCache(
                PicassoHelper.STORAGE_BASE_URL + imageModelArrayList.get(order).getImage(),
                activityProductScreenBinding.productIV,
                PicassoHelper.CENTER_INSIDE,
                null,
                null);
    }

    @Override
    public void onAddToCartClicked(Integer quantity, Integer sizeID, Integer colorID) {
        productScreenViewModel.addToCart(currentProductModel.getId(), quantity, sizeID, colorID);
    }

}
