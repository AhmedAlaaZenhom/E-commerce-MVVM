package com.intcore.internship.ecommerce.data.remote.helperModels.login;

import com.google.gson.annotations.SerializedName;
import com.intcore.internship.ecommerce.data.models.UserModel;

import java.util.List;

public class LoginResponseModel {

    @SerializedName("user")
    private UserModel userModel;

    @SerializedName("errors")
    private List<LoginErrorModel> errorModelList;

    public UserModel getUserModel() {
        return userModel;
    }

    public List<LoginErrorModel> getErrorModelList() {
        return errorModelList;
    }

}
