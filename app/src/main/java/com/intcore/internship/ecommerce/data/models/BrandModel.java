package com.intcore.internship.ecommerce.data.models;

import com.google.gson.annotations.SerializedName;
import com.intcore.internship.ecommerce.data.local.TablesNames;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = TablesNames.BRANDS)
public class BrandModel implements Serializable {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private Integer id ;

    @ColumnInfo(name = "image")
    @SerializedName("image")
    private String imageUrl ;

    @ColumnInfo(name = "status")
    @SerializedName("status")
    private Integer status ;

    @ColumnInfo(name = "created_at")
    @SerializedName("created_at")
    private String createdAt ;

    @ColumnInfo(name = "updated_at")
    @SerializedName("updated_at")
    private String updatedAt ;

    @ColumnInfo(name = "name_ar")
    @SerializedName("name_ar")
    private String nameAR ;

    @ColumnInfo(name = "name_en")
    @SerializedName("name_en")
    private String nameEN ;

    public Integer getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
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

    public String getNameAR() {
        return nameAR;
    }

    public String getNameEN() {
        return nameEN;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public void setNameAR(String nameAR) {
        this.nameAR = nameAR;
    }

    public void setNameEN(String nameEN) {
        this.nameEN = nameEN;
    }
}
