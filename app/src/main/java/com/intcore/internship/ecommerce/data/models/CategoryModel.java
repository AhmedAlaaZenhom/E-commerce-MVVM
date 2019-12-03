package com.intcore.internship.ecommerce.data.models;

import com.google.gson.annotations.SerializedName;
import com.intcore.internship.ecommerce.data.local.TablesNames;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = TablesNames.CATEGORIES)
public class CategoryModel {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private Integer id ;

    @ColumnInfo(name = "name_ar")
    @SerializedName("name_ar")
    private String nameAR ;

    @ColumnInfo(name = "name_en")
    @SerializedName("name_en")
    private String nameEN ;

    @ColumnInfo(name = "status")
    @SerializedName("status")
    private Integer status ;

    @ColumnInfo(name = "created_at")
    @SerializedName("created_at")
    private String createdAt ;

    @ColumnInfo(name = "updated_at")
    @SerializedName("updated_at")
    private String updatedAt ;

    @ColumnInfo(name = "image")
    @SerializedName("image")
    private String imageURL ;

    @ColumnInfo(name = "special")
    @SerializedName("special")
    private Integer special ;

    @ColumnInfo(name = "number_of_products")
    @SerializedName("number_of_products")
    private Integer noOfProducts ;

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

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getImageURL() {
        return imageURL;
    }

    public Integer getSpecial() {
        return special;
    }

    public Integer getNoOfProducts() {
        return noOfProducts;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNameAR(String nameAR) {
        this.nameAR = nameAR;
    }

    public void setNameEN(String nameEN) {
        this.nameEN = nameEN;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setSpecial(Integer special) {
        this.special = special;
    }

    public void setNoOfProducts(Integer noOfProducts) {
        this.noOfProducts = noOfProducts;
    }
}
