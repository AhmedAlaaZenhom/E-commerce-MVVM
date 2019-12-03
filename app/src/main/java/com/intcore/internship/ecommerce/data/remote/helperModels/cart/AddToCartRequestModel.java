package com.intcore.internship.ecommerce.data.remote.helperModels.cart;

import com.google.gson.annotations.SerializedName;

public class AddToCartRequestModel {

    @SerializedName("api_token")
    private String apiToken ;

    @SerializedName("product_id")
    private Integer productID ;

    @SerializedName("quantity")
    private Integer quantity ;

    @SerializedName("size_id")
    private Integer sizeID ;

    @SerializedName("color_id")
    private Integer colorID ;

    public AddToCartRequestModel(String apiToken, Integer productID, Integer quantity, Integer sizeID, Integer colorID) {
        this.apiToken = apiToken;
        this.productID = productID;
        this.quantity = quantity;
        this.sizeID = sizeID;
        this.colorID = colorID;
    }

    public String getApiToken() {
        return apiToken;
    }

    public Integer getProductID() {
        return productID;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Integer getSizeID() {
        return sizeID;
    }

    public Integer getColorID() {
        return colorID;
    }
}
