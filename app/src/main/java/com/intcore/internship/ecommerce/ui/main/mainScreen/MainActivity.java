package com.intcore.internship.ecommerce.ui.main.mainScreen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.intcore.internship.ecommerce.BR;
import com.intcore.internship.ecommerce.R;
import com.intcore.internship.ecommerce.data.remote.helperModels.cart.CartsResponseModel;
import com.intcore.internship.ecommerce.data.remote.helperModels.profile.ProfileResponseModel;
import com.intcore.internship.ecommerce.data.models.CategoryModel;
import com.intcore.internship.ecommerce.data.models.UserModel;
import com.intcore.internship.ecommerce.databinding.ActivityMainBinding;
import com.intcore.internship.ecommerce.ui.baseClasses.BaseActivity;
import com.intcore.internship.ecommerce.ui.commonClasses.ActivitiesRequestCodes;
import com.intcore.internship.ecommerce.ui.main.cart.CartFragment;
import com.intcore.internship.ecommerce.ui.main.deals.DealsFragment;
import com.intcore.internship.ecommerce.ui.main.home.HomeFragment;
import com.intcore.internship.ecommerce.ui.main.profile.ProfileFragment;
import com.intcore.internship.ecommerce.ui.main.wishlist.WishListFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<ActivityMainBinding>
        implements SideBarCategoriesAdapter.ClickListener {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    private MainViewModel mainViewModel;
    private ActivityMainBinding activityMainBinding;

    private TextView toolbarTitleTV, toolbarCartCountTV;
    private Fragment currentFragment;
    private FragmentManager fragmentManager;

    private SideBarCategoriesAdapter sideBarCategoriesAdapter;
    private ArrayList<CategoryModel> sideBarCategoriesList;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public MainViewModel getViewModel() {
        mainViewModel = ViewModelProviders.of(this,
                getCompositionRoot().getViewModelProviderFactory()).get(MainViewModel.class);
        return mainViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityMainBinding = getViewDataBinding();
        setUpSideBar();
        setUpToolBar();

        mainViewModel.getCategories();
        mainViewModel.getProfile();

        fragmentManager = getSupportFragmentManager();
        setUpBottomNavigation();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainViewModel.getCarts();
    }

    private void setUpSideBar() {
        sideBarCategoriesList = new ArrayList<>();
        sideBarCategoriesAdapter = new SideBarCategoriesAdapter(sideBarCategoriesList, this);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        ((RecyclerView) activityMainBinding.sideBarLayout.findViewById(R.id.categoriesRV)).setLayoutManager(linearLayoutManager);
        ((RecyclerView) activityMainBinding.sideBarLayout.findViewById(R.id.categoriesRV)).setAdapter(sideBarCategoriesAdapter);
    }

    private void setUpToolBar() {
        setSupportActionBar((Toolbar) activityMainBinding.toolbar);

        toolbarTitleTV = activityMainBinding.toolbar.findViewById(R.id.toolbarTitleTV);
        toolbarCartCountTV = activityMainBinding.toolbar.findViewById(R.id.toolbarCartCountTV);

        activityMainBinding.toolbar.findViewById(R.id.toolbarMenuBtn).setVisibility(View.VISIBLE);
        activityMainBinding.toolbar.findViewById(R.id.toolbarMenuBtn).setOnClickListener(v -> {
            activityMainBinding.drawer.openDrawer(GravityCompat.START);
        });

        activityMainBinding.toolbar.findViewById(R.id.toolbarCartIV).setVisibility(View.VISIBLE);
        activityMainBinding.toolbar.findViewById(R.id.toolbarCartIV).setOnClickListener(
                v -> activityMainBinding.bottomNavigationView.setSelectedItemId(R.id.bottomNavCart));

        activityMainBinding.toolbar.findViewById(R.id.toolbarSearchIV).setVisibility(View.VISIBLE);
        activityMainBinding.toolbar.findViewById(R.id.toolbarSearchIV).setOnClickListener(v -> {
        });
    }

    @Override
    protected void setUpObservers() {
        super.setUpObservers();
        final Observer<CartsResponseModel> cartItemsObserver = cartItemModels -> {
            toolbarCartCountTV.setText(String.valueOf(cartItemModels.getTotalCount()));
            toolbarCartCountTV.setText(String.valueOf(cartItemModels.getTotalCount()));
            toolbarCartCountTV.setVisibility(cartItemModels.getTotalCount() == 0 ? View.GONE : View.VISIBLE);
        };
        mainViewModel.getCartItemsLD().observe(this, cartItemsObserver);

        final Observer<List<CategoryModel>> categoriesObserver = categoryModelList -> {
            sideBarCategoriesList.clear();
            sideBarCategoriesList.addAll(categoryModelList);
            sideBarCategoriesAdapter.notifyDataSetChanged();
        };
        mainViewModel.getCategoriesLD().observe(this, categoriesObserver);

        final Observer<ProfileResponseModel> profileObserver = profileResponseModel -> {
            final UserModel userModel = profileResponseModel.getUserModel();
            ((TextView) activityMainBinding.sideBarLayout.findViewById(R.id.userNameTV)).setText(userModel.getName());
            ((TextView) activityMainBinding.sideBarLayout.findViewById(R.id.userEmailTV)).setText(userModel.getEmail());
        };
        mainViewModel.getProfileResponseLD().observe(this, profileObserver);
    }

    private void setUpBottomNavigation() {
        activityMainBinding.bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.bottomNavHome:
                    setSelectedFragment(HomeFragment.TAG);
                    return true;
                case R.id.bottomNavDeals:
                    setSelectedFragment(DealsFragment.TAG);
                    return true;
                case R.id.bottomNavWishList:
                    setSelectedFragment(WishListFragment.TAG);
                    return true;
                case R.id.bottomNavCart:
                    setSelectedFragment(CartFragment.TAG);
                    return true;
                case R.id.bottomNavProfile:
                    setSelectedFragment(ProfileFragment.TAG);
                    return true;
                default:
                    return false;
            }
        });
        activityMainBinding.bottomNavigationView.setSelectedItemId(R.id.bottomNavHome);
    }

    private void setSelectedFragment(String fragmentTag) {
        int titleStringRes = 0;
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentTag);
        if (fragment == null) {
            if (fragmentTag.equals(HomeFragment.TAG)) {
                fragment = HomeFragment.newInstance();
                titleStringRes = R.string.home ;
            }
            else if (fragmentTag.equals(DealsFragment.TAG)) {
                fragment = DealsFragment.newInstance();
                titleStringRes = R.string.deals ;
            }
            else if (fragmentTag.equals(WishListFragment.TAG)) {
                fragment = WishListFragment.newInstance();
                titleStringRes = R.string.wishlist ;
            }
            else if (fragmentTag.equals(CartFragment.TAG)) {
                fragment = CartFragment.newInstance();
                titleStringRes = R.string.cart ;
            }
            else if (fragmentTag.equals(ProfileFragment.TAG)) {
                fragment = ProfileFragment.newInstance();
                titleStringRes = R.string.profile ;
            }
            else
                return;
        }
        replaceFragment(fragment, fragmentTag, titleStringRes);
    }

    private void replaceFragment(@NonNull Fragment fragment, @NonNull String tag, int titleStringRes) {
        if (!fragment.equals(currentFragment)) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.mainContentView, fragment, tag)
                    .commit();
            currentFragment = fragment;
            toolbarTitleTV.setText(titleStringRes);
        }
    }

    @Override
    public void onCategoryClicked(CategoryModel categoryModel) {
        activityMainBinding.drawer.closeDrawers();
    }

    @Override
    public void onBackPressed() {
        if (activityMainBinding.drawer.isDrawerOpen(GravityCompat.START))
            activityMainBinding.drawer.closeDrawers();
        else
            super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case ActivitiesRequestCodes.PRODUCT_SCREEN_REQUEST_CODE:
                if(resultCode==RESULT_OK)
                    setSelectedFragment(CartFragment.TAG);
                break;
        }
    }

    public void onCartContentsChanged(){
        mainViewModel.getCarts();
    }
}
