package com.intcore.internship.ecommerce.data.remote.helperModels.register;

import com.google.gson.annotations.SerializedName;
import com.intcore.internship.ecommerce.data.models.UserModel;

import java.util.List;

public class RegisterResponseModel {

    @SerializedName("user")
    private UserModel userModel;

    @SerializedName("errors")
    private List<RegisterErrorModel> errorModelList;

    public UserModel getUserModel() {
        return userModel;
    }

    public List<RegisterErrorModel> getErrorModelList() {
        return errorModelList;
    }
}
