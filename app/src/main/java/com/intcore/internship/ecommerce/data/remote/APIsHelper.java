package com.intcore.internship.ecommerce.data.remote;

import com.intcore.internship.ecommerce.data.remote.helperModels.activation.ActivationRequestModel;
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
import com.intcore.internship.ecommerce.data.remote.helperModels.filter.FilterDataResponseModel;
import com.intcore.internship.ecommerce.data.remote.helperModels.home.HomeResponseModel;
import com.intcore.internship.ecommerce.data.remote.helperModels.login.LoginResponseModel;
import com.intcore.internship.ecommerce.data.remote.helperModels.login.LoginRequestModel;
import com.intcore.internship.ecommerce.data.remote.helperModels.login.SocialLoginRequestModel;
import com.intcore.internship.ecommerce.data.remote.helperModels.order.OrderRequestModel;
import com.intcore.internship.ecommerce.data.remote.helperModels.order.OrderResponseModel;
import com.intcore.internship.ecommerce.data.remote.helperModels.products.ProductsResponseModel;
import com.intcore.internship.ecommerce.data.remote.helperModels.profile.ProfileResponseModel;
import com.intcore.internship.ecommerce.data.remote.helperModels.register.RegisterRequestModel;
import com.intcore.internship.ecommerce.data.remote.helperModels.register.RegisterResponseModel;
import com.intcore.internship.ecommerce.data.models.AddressModel;
import com.intcore.internship.ecommerce.data.models.CategoryModel;
import com.intcore.internship.ecommerce.data.models.OrderModel;
import com.intcore.internship.ecommerce.data.models.ProductModel;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIsHelper {

    private Retrofit mRetrofit;

    private Retrofit getRetrofit() {
        if (mRetrofit == null) {
            OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
            clientBuilder.addInterceptor(chain -> {
                final Request.Builder requestBuilder = chain.request().newBuilder();
                requestBuilder.header("Accept", "application/json");
                requestBuilder.header("Content-Type", "application/x-www-form-urlencoded");
                return chain.proceed(requestBuilder.build());
            });
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(EcommerceAPI.BASE_URL)
                    .client(clientBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return mRetrofit;
    }

    private EcommerceAPI getEcommerceAPI() {
        return getRetrofit().create(EcommerceAPI.class);
    }

    // Auth

    public Single<Response<LoginResponseModel>> loginUsingEmailAndPassword(String email, String password, String deviceID) {
        final LoginRequestModel loginRequestModel = new LoginRequestModel(email, password, deviceID, "android");
        return getEcommerceAPI().loginUsingEmailAndPassword(loginRequestModel);
    }

    public Single<Response<LoginResponseModel>> loginUsingSocialMedia(String socialID, String socialType) {
        final SocialLoginRequestModel socialLoginRequestModel = new SocialLoginRequestModel(socialID, socialType);
        return getEcommerceAPI().loginUsingSocialMedia(socialLoginRequestModel);
    }

    public Single<Response<RegisterResponseModel>> register(String name, String email, String phone, String password) {
        final RegisterRequestModel registerRequestModel = new RegisterRequestModel(name, email, phone, password);
        return getEcommerceAPI().register(registerRequestModel);
    }

    public Single<Response<ActivationResponseModel>> activateAccount(String code, String apiKey) {
        final ActivationRequestModel activationRequestModel = new ActivationRequestModel(code, apiKey);
        return getEcommerceAPI().activateAccount(activationRequestModel);
    }

    // Carts

    public Single<Response<CartsResponseModel>> getCarts(String apiToken) {
        return getEcommerceAPI().getCarts(apiToken);
    }

    public Single<Response<AddToCartResponseModel>> addToCart(AddToCartRequestModel addToCartRequestModel) {
        return getEcommerceAPI().addToCart(addToCartRequestModel);
    }

    public Single<Response<CartsResponseModel>> incrementCartItemQuantity(String apiToken, Integer cartItemID) {
        return getEcommerceAPI().updateCartItemQuantity(apiToken, cartItemID,1);
    }

    public Single<Response<CartsResponseModel>> decrementCartItemQuantity(String apiToken, Integer cartItemID) {
        return getEcommerceAPI().updateCartItemQuantity(apiToken, cartItemID,0);
    }

    public Single<Response<CartsResponseModel>> removeCartItem(String apiToken, Integer cartItemID) {
        return getEcommerceAPI().removeCartItem(cartItemID, apiToken);
    }

    // Home
    public Observable<Response<HomeResponseModel>> getHome(String apiToken) {
        return getEcommerceAPI().getHome(apiToken);
    }

    // Deals page

    public Single<Response<DealsPageResponseModel>> getDealsPage(String apiToken) {
        return getEcommerceAPI().getDealsPage(apiToken);
    }

    // Products

    public Single<Response<ProductModel>> getProduct(int productID, String apiToken) {
        return getEcommerceAPI().getProduct(productID, apiToken);
    }

    public Single<Response<ProductsResponseModel>> getProducts(
            String apiToken,
            String keyword,
            Integer categoryID,
            Integer subCategoryID,
            Integer brandID,
            Integer maxPrice,
            Integer minPrice,
            Integer sortBy
    ) {
        return getEcommerceAPI().getProducts(apiToken, keyword, categoryID, subCategoryID, brandID, maxPrice, minPrice, sortBy);
    }

    // filter

    public Single<Response<FilterDataResponseModel>> getFilterData(Integer categoryID){
        return getEcommerceAPI().getFilterData(categoryID);
    }

    // Categories

    public Single<Response<List<CategoryModel>>> getCategories() {
        return getEcommerceAPI().getCategories();
    }

    // Profile

    public Single<Response<ProfileResponseModel>> getProfile(String apiToken) {
        return getEcommerceAPI().getProfile(apiToken);
    }

    // Addresses

    public Single<Response<List<AddressModel>>> getAddresses(String apiToken){
        return getEcommerceAPI().getAddresses(apiToken);
    }

    public Single<Response<AddAddressResponseModel>> addAddress(AddAddressRequestModel addAddressRequestModel){
        return getEcommerceAPI().addAddress(addAddressRequestModel);
    }

    // Orders

    public Single<Response<List<OrderModel>>> getOrders(String apiToken){
        return getEcommerceAPI().getOrders(apiToken) ;
    }

    public Single<Response<OrderResponseModel>> createOrder(OrderRequestModel orderRequestModel){
        return getEcommerceAPI().createOrder(orderRequestModel) ;
    }

    // Wish list

    public Single<Response<ToggleFavStateResponseModel>> toggleFavouriteState(ToggleFavStateRequestModel toggleFavStateRequestModel){
        return getEcommerceAPI().toggleFavouriteState(toggleFavStateRequestModel) ;
    }

    public Single<Response<WishListResponseModel>> getWishList(String apiToken){
        return getEcommerceAPI().getWishList(apiToken);
    }

}
