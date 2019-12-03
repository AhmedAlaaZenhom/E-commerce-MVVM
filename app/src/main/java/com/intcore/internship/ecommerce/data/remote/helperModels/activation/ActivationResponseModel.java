package com.intcore.internship.ecommerce.data.remote.helperModels.activation;

import com.google.gson.annotations.SerializedName;
import com.intcore.internship.ecommerce.data.models.UserModel;

public class ActivationResponseModel {

    @SerializedName("user")
    private UserModel userModel;

    public UserModel getUserModel() {
        return userModel;
    }

}
