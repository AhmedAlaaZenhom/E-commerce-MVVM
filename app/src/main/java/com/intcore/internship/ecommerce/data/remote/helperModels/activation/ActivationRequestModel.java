package com.intcore.internship.ecommerce.data.remote.helperModels.activation;

import com.google.gson.annotations.SerializedName;

public class ActivationRequestModel {
    @SerializedName("code")
    private String code ;

    @SerializedName("api_token")
    private String apiToken ;

    public ActivationRequestModel(String code, String apiToken) {
        this.code = code;
        this.apiToken = apiToken;
    }

    public String getCode() {
        return code;
    }

    public String getApiToken() {
        return apiToken;
    }
}
