package com.intcore.internship.ecommerce.data.remote.helperModels.products;

import com.google.gson.annotations.SerializedName;
import com.intcore.internship.ecommerce.data.models.ProductModel;

import java.util.List;

public class ProductsResponseModel {

    @SerializedName("products")
    private ProductsListModel productsListModel;

    public List<ProductModel> getProductModelList() {
        return productsListModel.getProductModelList();
    }

    public static class ProductsListModel {

        @SerializedName("data")
        private List<ProductModel> productModelList;

        List<ProductModel> getProductModelList() {
            return productModelList;
        }

    }

}
