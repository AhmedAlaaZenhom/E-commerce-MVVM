package com.intcore.internship.ecommerce.data.remote.helperModels.favourite;

import com.google.gson.annotations.SerializedName;
import com.intcore.internship.ecommerce.data.models.ProductModel;

import java.util.List;

public class WishListResponseModel {

    @SerializedName("current_page")
    private Integer currentPage ;

    @SerializedName("data")
    private List<ProductModel> productModelList ;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public List<ProductModel> getProductModelList() {
        return productModelList;
    }

}
