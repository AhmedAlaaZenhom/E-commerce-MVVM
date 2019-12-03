package com.intcore.internship.ecommerce.data.remote.helperModels.home;

import com.google.gson.annotations.SerializedName;
import com.intcore.internship.ecommerce.data.local.helperEntities.home.BestSellerEntity;
import com.intcore.internship.ecommerce.data.local.helperEntities.home.NewArrivalEntity;
import com.intcore.internship.ecommerce.data.local.helperEntities.home.TopCategoryEntity;
import com.intcore.internship.ecommerce.data.models.CategoryModel;
import com.intcore.internship.ecommerce.data.models.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class HomeResponseModel {

    @SerializedName("new_arrival")
    private List<ProductModel> newArrivalModelList ;

    @SerializedName("top_categories")
    private List<CategoryModel> topCategoryModelList ;

    @SerializedName("best_seller")
    private List<ProductModel> bestSellerModelList ;

    @SerializedName("hot_deals")
    private List<ProductModel> hotDealModelList ;

    @SerializedName("side_menu_categories")
    private List<CategoryModel> sideMenuCategoryModelList ;

    public List<ProductModel> getNewArrivalModelList() {
        return newArrivalModelList;
    }

    public List<CategoryModel> getTopCategoryModelList() {
        return topCategoryModelList;
    }

    public List<ProductModel> getBestSellerModelList() {
        return bestSellerModelList;
    }

    public List<ProductModel> getHotDealModelList() {
        return hotDealModelList;
    }

    public List<CategoryModel> getSideMenuCategoryModelList() {
        return sideMenuCategoryModelList;
    }

    public void setNewArrivalModelList(List<ProductModel> newArrivalModelList) {
        this.newArrivalModelList = newArrivalModelList;
    }

    public void setTopCategoryModelList(List<CategoryModel> topCategoryModelList) {
        this.topCategoryModelList = topCategoryModelList;
    }

    public void setBestSellerModelList(List<ProductModel> bestSellerModelList) {
        this.bestSellerModelList = bestSellerModelList;
    }

    public void setHotDealModelList(List<ProductModel> hotDealModelList) {
        this.hotDealModelList = hotDealModelList;
    }

    public void setSideMenuCategoryModelList(List<CategoryModel> sideMenuCategoryModelList) {
        this.sideMenuCategoryModelList = sideMenuCategoryModelList;
    }

    public List<NewArrivalEntity> getNewArrivalsEntityList(){
        if(newArrivalModelList==null)
            return null ;
        final List<NewArrivalEntity> newArrivalEntityList = new ArrayList<>();
        for(ProductModel productModel : newArrivalModelList){
            final NewArrivalEntity newArrivalEntity = new NewArrivalEntity() ;
            newArrivalEntity.setProductID(productModel.getId());
            newArrivalEntityList.add(newArrivalEntity);
        }
        return newArrivalEntityList ;
    }

    public List<BestSellerEntity> getBestSellersEntityList(){
        if(bestSellerModelList==null)
            return null ;
        final List<BestSellerEntity> bestSellerEntityList = new ArrayList<>();
        for(ProductModel productModel : bestSellerModelList){
            final BestSellerEntity bestSellerEntity = new BestSellerEntity() ;
            bestSellerEntity.setProductID(productModel.getId());
            bestSellerEntityList.add(bestSellerEntity);
        }
        return bestSellerEntityList ;
    }

    public List<TopCategoryEntity> getTopCategoryEntityList(){
        if(topCategoryModelList==null)
            return null ;
        final List<TopCategoryEntity> topCategoryEntityList = new ArrayList<>();
        for(CategoryModel categoryModel : topCategoryModelList){
            final TopCategoryEntity topCategoryEntity = new TopCategoryEntity() ;
            topCategoryEntity.setCategoryID(categoryModel.getId());
            topCategoryEntityList.add(topCategoryEntity);
        }
        return topCategoryEntityList ;
    }
}
