package com.intcore.internship.ecommerce.data.local.dao.home;

import com.intcore.internship.ecommerce.data.local.helperEntities.home.BestSellerEntity;
import com.intcore.internship.ecommerce.data.models.ProductModel;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import static com.intcore.internship.ecommerce.data.local.TablesNames.BEST_SELLER;
import static com.intcore.internship.ecommerce.data.local.TablesNames.PRODUCTS;

@Dao
public interface BestSellersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBestSellers(List<BestSellerEntity> bestSellerEntityList);

    @Query("SELECT * from " + PRODUCTS + " INNER JOIN "+BEST_SELLER+" ON "+BEST_SELLER + ".product_id=" + PRODUCTS + ".id")
    List<ProductModel> getBestSellerProducts();

    @Query("DELETE FROM "+BEST_SELLER)
    void deleteAll();
}
