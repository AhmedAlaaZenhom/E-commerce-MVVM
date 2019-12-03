package com.intcore.internship.ecommerce.data.remote.helperModels.cart;

import com.google.gson.annotations.SerializedName;
import com.intcore.internship.ecommerce.data.models.CartItemModel;

import java.util.List;

public class AddToCartResponseModel {

    @SerializedName("carts")
    private List<CartItemModel> cartItemModelList ;

    public List<CartItemModel> getCartItemModelList() {
        return cartItemModelList;
    }

}
