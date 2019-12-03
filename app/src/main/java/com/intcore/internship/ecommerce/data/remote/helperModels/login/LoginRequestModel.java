package com.intcore.internship.ecommerce.data.remote.helperModels.login;

import com.google.gson.annotations.SerializedName;

public class LoginRequestModel {

    @SerializedName("name")
    private String name ;

    @SerializedName("password")
    private String password ;

    @SerializedName("mobile_token")
    private String mobileToken ;

    @SerializedName("os")
    private String os ;

    public LoginRequestModel(String name, String password, String mobileToken, String os) {
        this.name = name;
        this.password = password;
        this.mobileToken = mobileToken;
        this.os = os;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getMobileToken() {
        return mobileToken;
    }

    public String getOs() {
        return os;
    }
}
