package com.intcore.internship.ecommerce.data.remote.helperModels.order;

import com.google.gson.annotations.SerializedName;
import com.intcore.internship.ecommerce.data.models.ProductModel;

public class OrderItemModel {

    @SerializedName("id")
    private Integer id;

    @SerializedName("order_id")
    private Integer orderID;

    @SerializedName("product_id")
    private Integer productID;

    @SerializedName("quantity")
    private String quantity;

    @SerializedName("price")
    private String price;

    @SerializedName("total")
    private Integer total;

    @SerializedName("product")
    private ProductModel productModel;

    public Integer getId() {
        return id;
    }

    public Integer getOrderID() {
        return orderID;
    }

    public Integer getProductID() {
        return productID;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getPrice() {
        return price;
    }

    public Integer getTotal() {
        return total;
    }

    public ProductModel getProductModel() {
        return productModel;
    }
}
