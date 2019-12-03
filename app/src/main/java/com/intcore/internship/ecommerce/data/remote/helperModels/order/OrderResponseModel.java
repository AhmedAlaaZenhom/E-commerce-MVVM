package com.intcore.internship.ecommerce.data.remote.helperModels.order;

import com.google.gson.annotations.SerializedName;
import com.intcore.internship.ecommerce.data.models.OrderModel;

public class OrderResponseModel {

    @SerializedName("data")
    private OrderModel orderModel ;

    public OrderModel getOrderModel() {
        return orderModel;
    }
}
