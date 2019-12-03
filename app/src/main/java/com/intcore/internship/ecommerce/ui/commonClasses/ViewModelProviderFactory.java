package com.intcore.internship.ecommerce.ui.commonClasses;

import android.app.Application;

import com.intcore.internship.ecommerce.ui.addresses.AddAddressViewModel;
import com.intcore.internship.ecommerce.ui.addresses.AddressesViewModel;
import com.intcore.internship.ecommerce.ui.checkout.CheckoutViewModel;
import com.intcore.internship.ecommerce.ui.main.cart.CartViewModel;
import com.intcore.internship.ecommerce.ui.main.deals.DealsViewModel;
import com.intcore.internship.ecommerce.ui.main.home.HomeViewModel;
import com.intcore.internship.ecommerce.ui.main.profile.ProfileViewModel;
import com.intcore.internship.ecommerce.ui.main.wishlist.WishListViewModel;
import com.intcore.internship.ecommerce.ui.orders.OrdersViewModel;
import com.intcore.internship.ecommerce.ui.product.ProductScreenViewModel;
import com.intcore.internship.ecommerce.ui.register.RegisterViewModel;
import com.intcore.internship.ecommerce.ui.login.LoginViewModel;
import com.intcore.internship.ecommerce.ui.main.mainScreen.MainViewModel;
import com.intcore.internship.ecommerce.ui.splash.SplashViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ViewModelProviderFactory extends ViewModelProvider.NewInstanceFactory {

    private final Application application;

    public ViewModelProviderFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SplashViewModel.class)) {
            //noinspection unchecked
            return (T) new SplashViewModel(application);
        } else if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            //noinspection unchecked
            return (T) new LoginViewModel(application);
        } else if (modelClass.isAssignableFrom(MainViewModel.class)) {
            //noinspection unchecked
            return (T) new MainViewModel(application);
        } else if (modelClass.isAssignableFrom(RegisterViewModel.class)) {
            //noinspection unchecked
            return (T) new RegisterViewModel(application);
        } else if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            //noinspection unchecked
            return (T) new HomeViewModel(application);
        } else if (modelClass.isAssignableFrom(CartViewModel.class)) {
            //noinspection unchecked
            return (T) new CartViewModel(application);
        } else if (modelClass.isAssignableFrom(WishListViewModel.class)) {
            //noinspection unchecked
            return (T) new WishListViewModel(application);
        } else if (modelClass.isAssignableFrom(DealsViewModel.class)) {
            //noinspection unchecked
            return (T) new DealsViewModel(application);
        } else if (modelClass.isAssignableFrom(ProfileViewModel.class)) {
            //noinspection unchecked
            return (T) new ProfileViewModel(application);
        } else if (modelClass.isAssignableFrom(ProductScreenViewModel.class)) {
            //noinspection unchecked
            return (T) new ProductScreenViewModel(application);
        } else if (modelClass.isAssignableFrom(CheckoutViewModel.class)) {
            //noinspection unchecked
            return (T) new CheckoutViewModel(application);
        } else if (modelClass.isAssignableFrom(AddressesViewModel.class)) {
            //noinspection unchecked
            return (T) new AddressesViewModel(application);
        } else if (modelClass.isAssignableFrom(AddAddressViewModel.class)) {
            //noinspection unchecked
            return (T) new AddAddressViewModel(application);
        } else if (modelClass.isAssignableFrom(OrdersViewModel.class)) {
            //noinspection unchecked
            return (T) new OrdersViewModel(application);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
