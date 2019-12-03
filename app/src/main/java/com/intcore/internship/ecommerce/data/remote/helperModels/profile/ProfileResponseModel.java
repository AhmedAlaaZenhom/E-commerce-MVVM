package com.intcore.internship.ecommerce.data.remote.helperModels.profile;

import com.google.gson.annotations.SerializedName;
import com.intcore.internship.ecommerce.data.models.UserModel;


public class ProfileResponseModel {

    @SerializedName("user")
    private UserModel userModel ;

    public UserModel getUserModel() {
        return userModel;
    }
}
