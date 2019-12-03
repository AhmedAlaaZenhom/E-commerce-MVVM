package com.intcore.internship.ecommerce.data.local.dao.common;

import com.intcore.internship.ecommerce.data.models.CategoryModel;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

@Dao
public interface CategoryModelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCategories(List<CategoryModel> categoryModelList);

}
