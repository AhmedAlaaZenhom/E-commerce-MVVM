package com.intcore.internship.ecommerce.data.remote.helperModels.register;

import com.google.gson.annotations.SerializedName;

public class RegisterErrorModel {
    @SerializedName("name")
    private String name ;

    @SerializedName("message")
    private String message ;

    @SerializedName("code")
    private Integer code ;

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }

}
