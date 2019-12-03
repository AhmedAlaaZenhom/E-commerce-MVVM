package com.intcore.internship.ecommerce.data.local;

import com.intcore.internship.ecommerce.data.local.dao.common.CategoryModelDao;
import com.intcore.internship.ecommerce.data.local.dao.common.ProductModelDao;
import com.intcore.internship.ecommerce.data.local.dao.home.BestSellersDao;
import com.intcore.internship.ecommerce.data.local.dao.home.NewArrivalDao;
import com.intcore.internship.ecommerce.data.local.dao.home.TopCategoriesDao;
import com.intcore.internship.ecommerce.data.local.helperEntities.home.BestSellerEntity;
import com.intcore.internship.ecommerce.data.local.helperEntities.home.NewArrivalEntity;
import com.intcore.internship.ecommerce.data.local.helperEntities.home.TopCategoryEntity;
import com.intcore.internship.ecommerce.data.models.BrandModel;
import com.intcore.internship.ecommerce.data.models.CategoryModel;
import com.intcore.internship.ecommerce.data.models.ProductModel;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {
        ProductModel.class,
        CategoryModel.class,
        BrandModel.class,
        NewArrivalEntity.class,
        TopCategoryEntity.class,
        BestSellerEntity.class},
        version = 1,
        exportSchema = false)
public abstract class ApplicationDatabase extends RoomDatabase {

    public abstract ProductModelDao productModelDao();

    public abstract CategoryModelDao categoryModelDao();

    public abstract NewArrivalDao newArrivalDao();

    public abstract BestSellersDao bestSellersDao();

    public abstract TopCategoriesDao topCategoriesDao();

}
