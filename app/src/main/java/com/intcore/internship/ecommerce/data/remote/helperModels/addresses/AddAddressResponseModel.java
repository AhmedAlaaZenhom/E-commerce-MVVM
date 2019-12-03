package com.intcore.internship.ecommerce.data.remote.helperModels.addresses;

import com.google.gson.annotations.SerializedName;
import com.intcore.internship.ecommerce.data.models.AddressModel;

public class AddAddressResponseModel {

    @SerializedName("data")
    private AddressModel addressModel ;

    @SerializedName("message")
    private String message ;

    public AddressModel getAddressModel() {
        return addressModel;
    }

    public String getMessage() {
        return message;
    }

}
