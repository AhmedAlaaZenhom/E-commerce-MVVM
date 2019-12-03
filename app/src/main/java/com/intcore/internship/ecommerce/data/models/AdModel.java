package com.intcore.internship.ecommerce.data.models;

import com.google.gson.annotations.SerializedName;

public class AdModel {

    @SerializedName("id")
    private int id  ;

    @SerializedName("name")
    private String name ;

    @SerializedName("url")
    private String url ;

    @SerializedName("image")
    private String imageUrl ;

    @SerializedName("created_at")
    private String createdAt ;

    @SerializedName("updated_at")
    private String updatedAt ;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}
