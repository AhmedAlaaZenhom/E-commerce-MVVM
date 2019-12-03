package com.intcore.internship.ecommerce.data.models;

import com.google.gson.annotations.SerializedName;

public class ImageModel {

    @SerializedName("id")
    private Integer id ;

    @SerializedName("image")
    private String image ;

    @SerializedName("product_id")
    private Integer productID ;

    @SerializedName("created_at")
    private String createdAt ;

    @SerializedName("updated_at")
    private String updatedAt ;

    public Integer getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public Integer getProductID() {
        return productID;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}
