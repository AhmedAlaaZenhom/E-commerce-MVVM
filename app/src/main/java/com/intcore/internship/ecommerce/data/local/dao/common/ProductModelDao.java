package com.intcore.internship.ecommerce.data.local.dao.common;

import com.intcore.internship.ecommerce.data.models.ProductModel;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

@Dao
public interface ProductModelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProducts(List<ProductModel> productModelList);

}
