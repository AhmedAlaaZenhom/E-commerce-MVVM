package com.intcore.internship.ecommerce.data.remote.helperModels.login;

import com.google.gson.annotations.SerializedName;

public class SocialLoginRequestModel {

    @SerializedName("social_id")
    private String socialID ;

    @SerializedName("social_type")
    private String socialType;

    public SocialLoginRequestModel(String socialID, String socialType) {
        this.socialID = socialID;
        this.socialType = socialType;
    }
}
