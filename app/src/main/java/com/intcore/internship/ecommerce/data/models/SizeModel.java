package com.intcore.internship.ecommerce.data.models;

import com.google.gson.annotations.SerializedName;

public class SizeModel {

    @SerializedName("id")
    private Integer id ;

    @SerializedName("size")
    private String size ;

    @SerializedName("price")
    private Float price ;

    @SerializedName("productID")
    private Integer productID ;

    @SerializedName("created_at")
    private String createdAt ;

    @SerializedName("updated_at")
    private String updatedAt ;

    public Integer getId() {
        return id;
    }

    public String getSize() {
        return size;
    }

    public Float getPrice() {
        return price;
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
