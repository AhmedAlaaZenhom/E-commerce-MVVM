package com.intcore.internship.ecommerce.data.models;

import com.google.gson.annotations.SerializedName;

public class OrderModel {

    @SerializedName("id")
    private Integer id;

    @SerializedName("user_id")
    private Integer userID;

    @SerializedName("shipping_name")
    private String shippingName;

    @SerializedName("payment_method")
    private String paymentMethod;

    @SerializedName("amount")
    private String amount;

    @SerializedName("shipping")
    private String shippingPrice;

    @SerializedName("status")
    private String status;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("address_id")
    private Integer addressID ;

    @SerializedName("type")
    private String type ;

    @SerializedName("expected_deliver_date")
    private String expectedDeliverDate ;

    @SerializedName("human_status")
    private String humanStatus ;

    @SerializedName("class_status")
    private String classStatus ;

    @SerializedName("total_amount")
    private Float totalPriceAmmount ;

    @SerializedName("class_payment")
    private String classPayment ;

    public Integer getId() {
        return id;
    }

    public Integer getUserID() {
        return userID;
    }

    public String getShippingName() {
        return shippingName;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getAmount() {
        return amount;
    }

    public String getShippingPrice() {
        return shippingPrice;
    }

    public String getStatus() {
        return status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public Integer getAddressID() {
        return addressID;
    }

    public String getType() {
        return type;
    }

    public String getExpectedDeliverDate() {
        return expectedDeliverDate;
    }

    public String getHumanStatus() {
        return humanStatus;
    }

    public String getClassStatus() {
        return classStatus;
    }

    public Float getTotalPriceAmmount() {
        return totalPriceAmmount;
    }

    public String getClassPayment() {
        return classPayment;
    }
}
