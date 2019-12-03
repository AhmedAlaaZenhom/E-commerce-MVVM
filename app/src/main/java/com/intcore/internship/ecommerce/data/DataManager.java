package com.intcore.internship.ecommerce.data;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.provider.Settings;

import com.intcore.internship.ecommerce.data.local.DbHelper;
import com.intcore.internship.ecommerce.data.local.helperEntities.home.BestSellerEntity;
import com.intcore.internship.ecommerce.data.local.helperEntities.home.NewArrivalEntity;
import com.intcore.internship.ecommerce.data.local.helperEntities.home.TopCategoryEntity;
import com.intcore.internship.ecommerce.data.remote.helperModels.activation.ActivationResponseModel;
import com.intcore.internship.ecommerce.data.remote.helperModels.addresses.AddAddressRequestModel;
import com.intcore.internship.ecommerce.data.remote.helperModels.addresses.AddAddressResponseModel;
import com.intcore.internship.ecommerce.data.remote.helperModels.cart.AddToCartRequestModel;
import com.intcore.internship.ecommerce.data.remote.helperModels.cart.AddToCartResponseModel;
import com.intcore.internship.ecommerce.data.remote.helperModels.cart.CartsResponseModel;
import com.intcore.internship.ecommerce.data.remote.helperModels.deals.DealsPageResponseModel;
import com.intcore.internship.ecommerce.data.remote.helperModels.favourite.ToggleFavStateRequestModel;
import com.intcore.internship.ecommerce.data.remote.helperModels.favourite.ToggleFavStateResponseModel;
import com.intcore.internship.ecommerce.data.remote.helperModels.favourite.WishListResponseModel;
import com.intcore.internship.ecommerce.data.remote.helperModels.home.HomeResponseModel;
import com.intcore.internship.ecommerce.data.remote.helperModels.login.LoginResponseModel;
import com.intcore.internship.ecommerce.data.remote.APIsHelper;
import com.intcore.internship.ecommerce.data.remote.helperModels.order.OrderRequestModel;
import com.intcore.internship.ecommerce.data.remote.helperModels.order.OrderResponseModel;
import com.intcore.internship.ecommerce.data.remote.helperModels.profile.ProfileResponseModel;
import com.intcore.internship.ecommerce.data.remote.helperModels.register.RegisterResponseModel;
import com.intcore.internship.ecommerce.data.models.AddressModel;
import com.intcore.internship.ecommerce.data.models.CategoryModel;
import com.intcore.internship.ecommerce.data.models.OrderModel;
import com.intcore.internship.ecommerce.data.models.ProductModel;
import com.intcore.internship.ecommerce.data.sharedPreferences.PreferenceHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

import static android.content.Context.ACTIVITY_SERVICE;

public class DataManager {

    private Context applicationContext;
    private DbHelper dbHelper ;
    private APIsHelper apIsHelper;
    private PreferenceHelper preferenceHelper;

    public DataManager(Context applicationContext) {
        this.applicationContext = applicationContext;
        dbHelper = new DbHelper(applicationContext);
        apIsHelper = new APIsHelper();
        preferenceHelper = new PreferenceHelper(applicationContext);
    }

    // Pure Networking

    public Single<Response<LoginResponseModel>> loginUsingEmailAndPassword(String email, String password) {
        return apIsHelper.loginUsingEmailAndPassword(email, password, getDeviceId());
    }

    public Single<Response<RegisterResponseModel>> register(String name, String email, String phone, String password) {
        return apIsHelper.register(name, email, phone, password);
    }

    public Single<Response<ActivationResponseModel>> activateAccount(String code, String apiKey) {
        return apIsHelper.activateAccount(code, apiKey);
    }

    public Single<Response<CartsResponseModel>> getCarts() {
        return apIsHelper.getCarts(getUserApiToken());
    }

    public Single<Response<AddToCartResponseModel>> addToCart(Integer productID , Integer quantity , Integer sizeID , Integer colorID) {
        return apIsHelper.addToCart(new AddToCartRequestModel(getUserApiToken(), productID, quantity, sizeID, colorID));
    }

    public Single<Response<CartsResponseModel>> incrementCartItemQuantity(Integer cartItemID) {
        return apIsHelper.incrementCartItemQuantity(getUserApiToken(), cartItemID);
    }

    public Single<Response<CartsResponseModel>> decrementCartItemQuantity(Integer cartItemID) {
        return apIsHelper.decrementCartItemQuantity(getUserApiToken(), cartItemID);
    }

    public Single<Response<CartsResponseModel>> removeCartItem(Integer cartItemID) {
        return apIsHelper.removeCartItem(getUserApiToken(), cartItemID);
    }

    public Single<Response<DealsPageResponseModel>> getDealsPage() {
        return apIsHelper.getDealsPage(getUserApiToken());
    }

    public Single<Response<ProductModel>> getProduct(int productID) {
        return apIsHelper.getProduct(productID, getUserApiToken());
    }

    public Single<Response<List<CategoryModel>>> getCategories() {
        return apIsHelper.getCategories();
    }

    public Single<Response<ProfileResponseModel>> getProfile() {
        return apIsHelper.getProfile(getUserApiToken());
    }

    public Single<Response<List<AddressModel>>> getAddresses(){
        return apIsHelper.getAddresses(getUserApiToken());
    }

    public Single<Response<AddAddressResponseModel>> addAddress(String city, String street, String building, String floor, String apartment, String landmark, String phoneNumber, String notes) {
        return apIsHelper.addAddress(new AddAddressRequestModel(
                city,
                street,
                building,
                floor,
                apartment,
                landmark,
                phoneNumber,
                notes,
                getUserApiToken()));
    }

    public Single<Response<List<OrderModel>>> getOrders(){
        return apIsHelper.getOrders(getUserApiToken()) ;
    }

    public Single<Response<OrderResponseModel>> createOrder(Integer addressID){
        return apIsHelper.createOrder(new OrderRequestModel(getUserApiToken(), addressID, "2")) ;
    }

    public Single<Response<ToggleFavStateResponseModel>> toggleFavouriteState(Integer productID){
        return apIsHelper.toggleFavouriteState(new ToggleFavStateRequestModel(getUserApiToken(),productID)) ;
    }

    public Single<Response<WishListResponseModel>> getWishList(){
        return apIsHelper.getWishList(getUserApiToken());
    }

    // Mixed Repository

    public Observable<HomeResponseModel> getHome() {
        return Observable.mergeDelayError(
                dbHelper.getHomeData(),
                apIsHelper.getHome(getUserApiToken())
                        .map(response -> (response != null && response.isSuccessful()) ? response.body() : null)
                        .doOnNext(homeResponseModel -> dbHelper.insertHomeData(homeResponseModel))
        );
    }

    // Shared preferences

    public void setUserLoggedIn() {
        preferenceHelper.setUserLoggedIn();
    }

    public boolean isUserLoggedIn() {
        return preferenceHelper.isUserLoggedIn();
    }

    public void saveUserData(String userID, String userName, String userApiToken) {
        preferenceHelper.saveUserData(userID, userName, userApiToken);
    }

    public String getUserId() {
        return preferenceHelper.getUserId();
    }

    public String getUserName() {
        return preferenceHelper.getUserName();
    }

    private String getUserApiToken() {
        return preferenceHelper.getUserApiToken();
    }

    // Device data

    private String getDeviceId() {
        return Settings.Secure.getString(applicationContext.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public void signOutAndExit(Activity activity){
        ((ActivityManager)activity.getSystemService(ACTIVITY_SERVICE)).clearApplicationUserData();
        activity.finishAffinity();
    }

}
