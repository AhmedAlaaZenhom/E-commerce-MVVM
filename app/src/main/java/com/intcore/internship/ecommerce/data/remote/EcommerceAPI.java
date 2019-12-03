package com.intcore.internship.ecommerce.data.remote;

import com.intcore.internship.ecommerce.data.remote.helperModels.activation.ActivationRequestModel;
import com.intcore.internship.ecommerce.data.remote.helperModels.activation.ActivationResponseModel;
import com.intcore.internship.ecommerce.data.remote.helperModels.addresses.AddAddressRequestModel;
import com.intcore.internship.ecommerce.data.remote.helperModels.addresses.AddAddressResponseModel;
import com.intcore.internship.ecommerce.data.remote.helperModels.cart.AddToCartRequestModel;
import com.intcore.internship.ecommerce.data.remote.helperModels.cart.AddToCartResponseModel;
import com.intcore.internship.ecommerce.data.remote.helperModels.cart.CartsResponseModel;
import com.intcore.internship.ecommerce.data.remote.helperModels.deals.DealsPageResponseModel;
import com.intcore.internship.ecommerce.data.remote.helperModels.favourite.WishListResponseModel;
import com.intcore.internship.ecommerce.data.remote.helperModels.favourite.ToggleFavStateRequestModel;
import com.intcore.internship.ecommerce.data.remote.helperModels.favourite.ToggleFavStateResponseModel;
import com.intcore.internship.ecommerce.data.remote.helperModels.home.HomeResponseModel;
import com.intcore.internship.ecommerce.data.remote.helperModels.login.LoginResponseModel;
import com.intcore.internship.ecommerce.data.remote.helperModels.login.LoginRequestModel;
import com.intcore.internship.ecommerce.data.remote.helperModels.order.OrderRequestModel;
import com.intcore.internship.ecommerce.data.remote.helperModels.order.OrderResponseModel;
import com.intcore.internship.ecommerce.data.remote.helperModels.profile.ProfileResponseModel;
import com.intcore.internship.ecommerce.data.remote.helperModels.register.RegisterResponseModel;
import com.intcore.internship.ecommerce.data.remote.helperModels.register.RegisterRequestModel;
import com.intcore.internship.ecommerce.data.models.AddressModel;
import com.intcore.internship.ecommerce.data.models.CategoryModel;
import com.intcore.internship.ecommerce.data.models.OrderModel;
import com.intcore.internship.ecommerce.data.models.ProductModel;


import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EcommerceAPI {

    String BASE_URL = "https://e-commerce-dev.intcore.net/api/v1/";

    // Authentication

    @POST("user/auth/signin")
    Single<Response<LoginResponseModel>> loginUsingEmailAndPassword(@Body LoginRequestModel loginRequestModel);

    @POST("user/auth/signup")
    Single<Response<RegisterResponseModel>> register(@Body RegisterRequestModel registerRequestModel);

    @POST("user/auth/active-account")
    Single<Response<ActivationResponseModel>> activateAccount(@Body ActivationRequestModel activationRequestModel);

    // Cart

    @GET("user/app/cart/get-carts")
    Single<Response<CartsResponseModel>> getCarts(@Query("api_token") String apiToken);

    @POST("user/app/cart/add")
    Single<Response<AddToCartResponseModel>> addToCart(@Body AddToCartRequestModel addToCartRequestModel) ;

    @PATCH("user/app/cart/update-cart")
    Single<Response<CartsResponseModel>> updateCartItemQuantity(
            @Query("api_token") String apiToken, @Query("cart_id") Integer cartItemID, @Query("quantity") Integer quantity);

    @DELETE("user/app/cart/delete/{id}")
    Single<Response<CartsResponseModel>> removeCartItem(
            @Path("id") Integer cartItemID, @Query("api_token") String apiToken);

    // Home

    @GET("user/app/home")
    Observable<Response<HomeResponseModel>> getHome(@Query("api_token") String apiToken);

    // Deals

    @GET("user/app/deals-page")
    Single<Response<DealsPageResponseModel>> getDealsPage(@Query("api_token") String apiToken);

    // Product

    @GET("user/app/product/{id}")
    Single<Response<ProductModel>> getProduct(@Path("id") int productID, @Query("api_token") String apiToken);

    // Categories

    @GET("user/app/categories")
    Single<Response<List<CategoryModel>>> getCategories() ;

    // Profile

    @GET("user/auth/get-profile")
    Single<Response<ProfileResponseModel>> getProfile(@Query("api_token") String apiToken) ;

    // Favourites

    @POST("user/app/action/favourite")
    Single<Response<ToggleFavStateResponseModel>> toggleFavouriteState(@Body ToggleFavStateRequestModel toggleFavStateRequestModel) ;

    @GET("user/auth/favourite")
    Single<Response<WishListResponseModel>> getWishList(@Query("api_token") String apiToken) ;

    // Addresses

    @GET("user/app/address")
    Single<Response<List<AddressModel>>> getAddresses(@Query("api_token") String apiToken);

    @POST("user/app/address")
    Single<Response<AddAddressResponseModel>> addAddress(@Body AddAddressRequestModel addAddressRequestModel);

    // Orders

    @GET("user/app/order")
    Single<Response<List<OrderModel>>> getOrders(@Query("api_token") String apiToken);

    @POST("user/app/order")
    Single<Response<OrderResponseModel>> createOrder(@Body OrderRequestModel orderRequestModel);
}
