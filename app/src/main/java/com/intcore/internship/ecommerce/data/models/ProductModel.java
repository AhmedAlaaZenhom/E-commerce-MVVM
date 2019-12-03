package com.intcore.internship.ecommerce.data.models;


import com.google.gson.annotations.SerializedName;
import com.intcore.internship.ecommerce.data.local.TablesNames;

import java.util.List;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = TablesNames.PRODUCTS)
public class ProductModel {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private Integer id;

    @ColumnInfo(name = "name_ar")
    @SerializedName("name_ar")
    private String nameAR;

    @ColumnInfo(name = "name_en")
    @SerializedName("name_en")
    private String nameEN;

    @ColumnInfo(name = "description_ar")
    @SerializedName("description_ar")
    private String descriptionAR;

    @ColumnInfo(name = "description_en")
    @SerializedName("description_en")
    private String descriptionEN;

    @ColumnInfo(name = "stock")
    @SerializedName("stock")
    private Integer stock;

    @ColumnInfo(name = "shipping_specification")
    @SerializedName("shipping_specification")
    private String shippingSpecs;

    @ColumnInfo(name = "sub_category_id")
    @SerializedName("sub_category_id")
    private Integer subCatID;

    @ColumnInfo(name = "created_at")
    @SerializedName("created_at")
    private String createdAT;

    @ColumnInfo(name = "price")
    @SerializedName("price")
    private Float price;

    @ColumnInfo(name = "brand_id")
    @SerializedName("brand_id")
    private Integer brandID;

    @ColumnInfo(name = "long_description_ar")
    @SerializedName("long_description_ar")
    private String longDescriptionAR;

    @ColumnInfo(name = "long_description_en")
    @SerializedName("long_description_en")
    private String longDescriptionEn;

    @ColumnInfo(name = "friendly_url")
    @SerializedName("friendly_url")
    private String friendlyUrl;

    @ColumnInfo(name = "count_paid")
    @SerializedName("count_paid")
    private Integer countPaid;

    @ColumnInfo(name = "url")
    @SerializedName("url")
    private String url;

    @ColumnInfo(name = "default_image")
    @SerializedName("default_image")
    private String defaultImage;

    @ColumnInfo(name = "is_fav")
    @SerializedName("is_fav")
    private Boolean isFav;

    @ColumnInfo(name = "total_rate")
    @SerializedName("total_rate")
    private Float totalRate;

    @ColumnInfo(name = "is_reviewed")
    @SerializedName("is_reviewed")
    private Boolean isReviewed;

    @ColumnInfo(name = "disable")
    @SerializedName("disable")
    private Integer disable;

    @Ignore
    @SerializedName("brand")
    private BrandModel brandModel;

    @Ignore
    @SerializedName("subcategory")
    private SubCategoryModel subCategoryModel;

    @Ignore
    @SerializedName("today_offer")
    private List<OfferModel> todayOfferModelList;

    @Ignore
    @SerializedName("offer")
    private List<OfferModel> offerModelList ;

    @Ignore
    @SerializedName("reviews")
    private List<ReviewModel> reviewModelList;

    @Ignore
    @SerializedName("colors")
    private List<ColorModel> colorModelList;

    @Ignore
    @SerializedName("sizes")
    private List<SizeModel> sizeModelList;

    @Ignore
    @SerializedName("images")
    private List<ImageModel> imageModelList;



    public Integer getId() {
        return id;
    }

    public String getNameAR() {
        return nameAR;
    }

    public String getNameEN() {
        return nameEN;
    }

    public String getDescriptionAR() {
        return descriptionAR;
    }

    public String getDescriptionEN() {
        return descriptionEN;
    }

    public Integer getStock() {
        return stock;
    }

    public String getShippingSpecs() {
        return shippingSpecs;
    }

    public Integer getSubCatID() {
        return subCatID;
    }

    public String getCreatedAT() {
        return createdAT;
    }

    public Float getPrice() {
        return price;
    }

    public Integer getBrandID() {
        return brandID;
    }

    public String getLongDescriptionAR() {
        return longDescriptionAR;
    }

    public String getLongDescriptionEn() {
        return longDescriptionEn;
    }

    public String getFriendlyUrl() {
        return friendlyUrl;
    }

    public Integer getCountPaid() {
        return countPaid;
    }

    public String getUrl() {
        return url;
    }

    public List<OfferModel> getTodayOfferModelList() {
        return todayOfferModelList;
    }

    public String getDefaultImage() {
        return defaultImage;
    }

    public Boolean isFav() {
        return isFav;
    }

    public Float getTotalRate() {
        return totalRate;
    }

    public Boolean getReviewed() {
        return isReviewed;
    }

    public Integer getDisable() {
        return disable;
    }

    public BrandModel getBrandModel() {
        return brandModel;
    }

    public SubCategoryModel getSubCategoryModel() {
        return subCategoryModel;
    }

    public List<OfferModel> getOfferModelList() {
        return offerModelList;
    }

    public List<ReviewModel> getReviewModelList() {
        return reviewModelList;
    }

    public List<ColorModel> getColorModelList() {
        return colorModelList;
    }

    public List<SizeModel> getSizeModelList() {
        return sizeModelList;
    }

    public List<ImageModel> getImageModelList() {
        return imageModelList;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public void setNameAR(String nameAR) {
        this.nameAR = nameAR;
    }

    public void setNameEN(String nameEN) {
        this.nameEN = nameEN;
    }

    public void setDescriptionAR(String descriptionAR) {
        this.descriptionAR = descriptionAR;
    }

    public void setDescriptionEN(String descriptionEN) {
        this.descriptionEN = descriptionEN;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public void setShippingSpecs(String shippingSpecs) {
        this.shippingSpecs = shippingSpecs;
    }

    public void setSubCatID(Integer subCatID) {
        this.subCatID = subCatID;
    }

    public void setCreatedAT(String createdAT) {
        this.createdAT = createdAT;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public void setBrandID(Integer brandID) {
        this.brandID = brandID;
    }

    public void setLongDescriptionAR(String longDescriptionAR) {
        this.longDescriptionAR = longDescriptionAR;
    }

    public void setLongDescriptionEn(String longDescriptionEn) {
        this.longDescriptionEn = longDescriptionEn;
    }

    public void setFriendlyUrl(String friendlyUrl) {
        this.friendlyUrl = friendlyUrl;
    }

    public void setCountPaid(Integer countPaid) {
        this.countPaid = countPaid;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDefaultImage(String defaultImage) {
        this.defaultImage = defaultImage;
    }

    public void setFav(Boolean fav) {
        isFav = fav;
    }

    public void setTotalRate(Float totalRate) {
        this.totalRate = totalRate;
    }

    public void setReviewed(Boolean reviewed) {
        isReviewed = reviewed;
    }

    public void setDisable(Integer disable) {
        this.disable = disable;
    }

    public void setBrandModel(BrandModel brandModel) {
        this.brandModel = brandModel;
    }

    public void setSubCategoryModel(SubCategoryModel subCategoryModel) {
        this.subCategoryModel = subCategoryModel;
    }

    public void setTodayOfferModelList(List<OfferModel> todayOfferModelList) {
        this.todayOfferModelList = todayOfferModelList;
    }

    public void setOfferModelList(List<OfferModel> offerModelList) {
        this.offerModelList = offerModelList;
    }

    public void setReviewModelList(List<ReviewModel> reviewModelList) {
        this.reviewModelList = reviewModelList;
    }

    public void setColorModelList(List<ColorModel> colorModelList) {
        this.colorModelList = colorModelList;
    }

    public void setSizeModelList(List<SizeModel> sizeModelList) {
        this.sizeModelList = sizeModelList;
    }

    public void setImageModelList(List<ImageModel> imageModelList) {
        this.imageModelList = imageModelList;
    }
}
