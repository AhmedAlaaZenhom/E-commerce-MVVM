package com.intcore.internship.ecommerce.data.models;

import com.google.gson.annotations.SerializedName;

public class CartItemModel {

    @SerializedName("id")
    private Integer id ;

    @SerializedName("user_id")
    private Integer userID ;

    @SerializedName("product_id")
    private Integer productID ;

    @SerializedName("quantity")
    private Integer quantity ;

    @SerializedName("created_at")
    private String createdAt ;

    @SerializedName("updated_at")
    private String updatedAt ;

    @SerializedName("product")
    private ProductModel productModel ;

    public Integer getId() {
        return id;
    }

    public Integer getUserID() {
        return userID;
    }

    public Integer getProductID() {
        return productID;
    }

    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity ;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public ProductModel getProductModel() {
        return productModel;
    }
}
