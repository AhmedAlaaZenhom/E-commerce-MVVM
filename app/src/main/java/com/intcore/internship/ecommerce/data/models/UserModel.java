package com.intcore.internship.ecommerce.data.models;

import com.google.gson.annotations.SerializedName;

public class UserModel {

    @SerializedName("id")
    private Integer id ;

    @SerializedName("name")
    private String name ;

    @SerializedName("email")
    private String email ;

    @SerializedName("phone")
    private String phone ;

    @SerializedName("gender")
    private String gender ;

    @SerializedName("birth_date")
    private String birthDate ;

    @SerializedName("activation")
    private Integer activation ;

    @SerializedName("type")
    private Integer type ;

    @SerializedName("image")
    private String image ;

    @SerializedName("cover")
    private String cover ;

    @SerializedName("reset_password_code")
    private String resetPasswordCode ;

    @SerializedName("api_token")
    private String apiToken ;

    @SerializedName("country_id")
    private String countryID ;

    @SerializedName("created_at")
    private String createdAt ;

    @SerializedName("updated_at")
    private String updatedAt ;

    @SerializedName("mobile_token")
    private String mobile_token ;

    @SerializedName("facebook_token")
    private String faceBookToken ;

    @SerializedName("google_token")
    private String googleToken ;

    @SerializedName("os")
    private String os ;

    @SerializedName("temp_phone_code")
    private String tempPhoneCode ;

    @SerializedName("address_id")
    private String addressID ;

    @SerializedName("social_id")
    private String socialID ;

    @SerializedName("social_type")
    private String socialType ;

    @SerializedName("cart_count")
    private String cartCount ;

    public UserModel() {
    }

    public Integer getId() {
        return id;
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

    public String getGender() {
        return gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public Integer getActivation() {
        return activation;
    }

    public Integer getType() {
        return type;
    }

    public String getImage() {
        return image;
    }

    public String getCover() {
        return cover;
    }

    public String getResetPasswordCode() {
        return resetPasswordCode;
    }

    public String getApiToken() {
        return apiToken;
    }

    public String getCountryID() {
        return countryID;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getMobile_token() {
        return mobile_token;
    }

    public String getFaceBookToken() {
        return faceBookToken;
    }

    public String getGoogleToken() {
        return googleToken;
    }

    public String getOs() {
        return os;
    }

    public String getTempPhoneCode() {
        return tempPhoneCode;
    }

    public String getAddressID() {
        return addressID;
    }

    public String getSocialID() {
        return socialID;
    }

    public String getSocialType() {
        return socialType;
    }

    public String getCartCount() {
        return cartCount;
    }
}
