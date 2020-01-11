package com.intcore.internship.ecommerce.data.remote.helperModels.filter;

import com.google.gson.annotations.SerializedName;
import com.intcore.internship.ecommerce.data.models.BrandModel;
import com.intcore.internship.ecommerce.data.models.SubCategoryModel;

import java.util.List;

public class FilterDataResponseModel {

    @SerializedName("max_price")
    private float maxPrice;

    @SerializedName("min_price")
    private float minPrice;

    @SerializedName("brands")
    private List<BrandModel> brandModelList;

    @SerializedName("subCategories")
    private List<SubCategoryModel> subCategoryModels;

    public float getMaxPrice() {
        return maxPrice;
    }

    public float getMinPrice() {
        return minPrice;
    }

    public List<BrandModel> getBrandModelList() {
        return brandModelList;
    }

    public List<SubCategoryModel> getSubCategoryModels() {
        return subCategoryModels;
    }
}
