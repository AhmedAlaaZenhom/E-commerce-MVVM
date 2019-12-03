package com.intcore.internship.ecommerce.data.local.dao.home;

import com.intcore.internship.ecommerce.data.local.helperEntities.home.NewArrivalEntity;
import com.intcore.internship.ecommerce.data.models.ProductModel;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import static com.intcore.internship.ecommerce.data.local.TablesNames.NEW_ARRIVALS;
import static com.intcore.internship.ecommerce.data.local.TablesNames.PRODUCTS;

@Dao
public interface NewArrivalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNewArrivals(List<NewArrivalEntity> newArrivalEntityList);

    @Query("SELECT * from " + PRODUCTS + " INNER JOIN "+NEW_ARRIVALS+" ON "+NEW_ARRIVALS + ".product_id=" + PRODUCTS + ".id")
    List<ProductModel> getNewArrivalProducts();

    @Query("DELETE FROM "+NEW_ARRIVALS)
    void deleteAll();
}
