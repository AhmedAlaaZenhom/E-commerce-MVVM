package com.intcore.internship.ecommerce.data.models;

import com.google.gson.annotations.SerializedName;

public class OfferModel {

    @SerializedName("id")
    private Integer id ;

    @SerializedName("percentage")
    private Integer percentage ;

    @SerializedName("start_date")
    private String startDate ;

    @SerializedName("end_date")
    private String endData ;

    @SerializedName("product_id")
    private Integer productID ;

    @SerializedName("created_at")
    private String createdAt ;

    @SerializedName("updated_at")
    private String updated_at ;

    @SerializedName("price")
    private Float priceAfterOffer ;

    @SerializedName("product")
    private ProductModel productModel ;

    public Integer getId() {
        return id;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndData() {
        return endData;
    }

    public Integer getProductID() {
        return productID;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public Float getPriceAfterOffer() {
        return priceAfterOffer;
    }

    public ProductModel getProductModel() {
        return productModel;
    }
}
