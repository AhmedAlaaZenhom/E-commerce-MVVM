package com.intcore.internship.ecommerce.data.models;

import com.google.gson.annotations.SerializedName;

public class SubCategoryModel {

    @SerializedName("id")
    private Integer id ;

    @SerializedName("name_ar")
    private String nameAR ;

    @SerializedName("name_en")
    private String nameEN ;

    @SerializedName("status")
    private Integer status ;

    @SerializedName("category_id")
    private Integer categoryID ;

    @SerializedName("created_at")
    private String createdAt ;

    @SerializedName("updated_at")
    private String updatedAt ;

    public Integer getId() {
        return id;
    }

    public String getNameAR() {
        return nameAR;
    }

    public String getNameEN() {
        return nameEN;
    }

    public Integer getStatus() {
        return status;
    }

    public Integer getCategoryID() {
        return categoryID;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}
