package com.intcore.internship.ecommerce.data.remote.helperModels.order;

import com.google.gson.annotations.SerializedName;

public class OrderRequestModel {

    @SerializedName("api_token")
    private String apiToken;

    @SerializedName("address_id")
    private Integer addressID ;

    @SerializedName("payment_method")
    private String paymentMethod ;

    public OrderRequestModel(String apiToken, Integer addressID, String paymentMethod) {
        this.apiToken = apiToken;
        this.addressID = addressID;
        this.paymentMethod = paymentMethod;
    }

    public String getApiToken() {
        return apiToken;
    }

    public Integer getAddressID() {
        return addressID;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

}
