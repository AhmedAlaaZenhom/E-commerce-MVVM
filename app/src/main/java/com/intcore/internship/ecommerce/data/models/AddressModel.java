package com.intcore.internship.ecommerce.data.models;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AddressModel implements Serializable {

    @SerializedName("id")
    private Integer id;

    @SerializedName("city")
    private String city;

    @SerializedName("street")
    private String street;

    @SerializedName("building")
    private String building;

    @SerializedName("floor")
    private String floor;

    @SerializedName("apartment")
    private String apartment;

    @SerializedName("phone")
    private String phone;

    @SerializedName("landmark")
    private String landmark;

    @SerializedName("notes")
    private String notes;

    @SerializedName("user_id")
    private Integer userID;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getBuilding() {
        return building;
    }

    public String getFloor() {
        return floor;
    }

    public String getApartment() {
        return apartment;
    }

    public String getPhone() {
        return phone;
    }

    public String getLandmark() {
        return landmark;
    }

    public String getNotes() {
        return notes;
    }

    public Integer getUserID() {
        return userID;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}
