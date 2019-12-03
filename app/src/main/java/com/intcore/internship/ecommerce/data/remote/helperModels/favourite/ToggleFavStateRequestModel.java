package com.intcore.internship.ecommerce.data.remote.helperModels.favourite;

import com.google.gson.annotations.SerializedName;

public class ToggleFavStateRequestModel {

    @SerializedName("api_token")
    private String apiToken ;

    @SerializedName("product_id")
    private Integer productID ;

    public ToggleFavStateRequestModel(String apiToken, Integer productID) {
        this.apiToken = apiToken;
        this.productID = productID;
    }

    public String getApiToken() {
        return apiToken;
    }

    public Integer getProductID() {
        return productID;
    }
}
