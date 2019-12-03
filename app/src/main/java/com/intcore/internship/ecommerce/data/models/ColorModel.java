package com.intcore.internship.ecommerce.data.models;

import com.google.gson.annotations.SerializedName;

public class ColorModel {

    @SerializedName("id")
    private Integer id ;

    @SerializedName("color")
    private String color ;

    @SerializedName("product_id")
    private Integer colorID ;

    @SerializedName("created_at")
    private String createdAt ;

    @SerializedName("updated_at")
    private String updatedAt ;

    public Integer getId() {
        return id;
    }

    public String getColor() {
        return color;
    }

    public Integer getColorID() {
        return colorID;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}
