package com.intcore.internship.ecommerce.data.remote.helperModels.register;

import com.google.gson.annotations.SerializedName;

public class RegisterRequestModel {

    @SerializedName("name")
    private String name ;

    @SerializedName("email")
    private String email ;

    @SerializedName("phone")
    private String phone ;

    @SerializedName("password")
    private String password ;

    public RegisterRequestModel(String name, String email, String phone, String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }
}
