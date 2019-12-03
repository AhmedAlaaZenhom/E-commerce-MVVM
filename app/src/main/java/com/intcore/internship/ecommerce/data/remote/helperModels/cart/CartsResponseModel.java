package com.intcore.internship.ecommerce.data.remote.helperModels.cart;

import com.google.gson.annotations.SerializedName;
import com.intcore.internship.ecommerce.data.models.CartItemModel;

import java.util.List;

public class CartsResponseModel {

    @SerializedName("carts")
    private List<CartItemModel> cartItemModelList ;

    @SerializedName("total_price")
    private Float totalPrice ;

    @SerializedName("total_items")
    private Integer totalCount ;

    @SerializedName("shipping")
    private String shipping ;

    public List<CartItemModel> getCartItemModelList() {
        return cartItemModelList;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public String getShipping() {
        return shipping;
    }
}
