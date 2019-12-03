package com.intcore.internship.ecommerce.data.remote.helperModels.favourite;

import com.google.gson.annotations.SerializedName;

public class ToggleFavStateResponseModel {

    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

}
