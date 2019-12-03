package com.intcore.internship.ecommerce.data.remote.helperModels.deals;

import com.google.gson.annotations.SerializedName;
import com.intcore.internship.ecommerce.data.models.AdModel;
import com.intcore.internship.ecommerce.data.models.BrandModel;
import com.intcore.internship.ecommerce.data.models.CategoryModel;
import com.intcore.internship.ecommerce.data.models.ProductModel;

import java.util.List;

public class DealsPageResponseModel {

    @SerializedName("ads")
    private List<AdModel> adModelList ;

    @SerializedName("hot_deals")
    private List<ProductModel> hotDealsModelList ;

    @SerializedName("top_categories")
    private List<CategoryModel> topCategoriesModelList ;

    @SerializedName("top_brand")
    private List<BrandModel> topBrandsModelList ;

    public List<AdModel> getAdModelList() {
        return adModelList;
    }

    public List<ProductModel> getHotDealsModelList() {
        return hotDealsModelList;
    }

    public List<CategoryModel> getTopCategoriesModelList() {
        return topCategoriesModelList;
    }

    public List<BrandModel> getTopBrandsModelList() {
        return topBrandsModelList;
    }
}
