package com.intcore.internship.ecommerce.data.remote.helperModels.addresses;

import com.google.gson.annotations.SerializedName;

public class AddAddressRequestModel {

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

    @SerializedName("landmark")
    private String landmark;

    @SerializedName("phone")
    private String phone;

    @SerializedName("notes")
    private String notes;

    @SerializedName("api_token")
    private String apiToken;

    public AddAddressRequestModel(
            String city,
            String street,
            String building,
            String floor,
            String apartment,
            String landmark,
            String phone,
            String notes,
            String apiToken) {
        this.city = city;
        this.street = street;
        this.building = building;
        this.floor = floor;
        this.apartment = apartment;
        this.landmark = landmark;
        this.phone = phone;
        this.notes = notes;
        this.apiToken = apiToken;
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

    public String getLandmark() {
        return landmark;
    }

    public String getPhone() {
        return phone;
    }

    public String getNotes() {
        return notes;
    }

    public String getApiToken() {
        return apiToken;
    }
}
