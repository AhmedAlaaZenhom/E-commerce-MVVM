package com.intcore.internship.ecommerce.data.local.dao.home;

import com.intcore.internship.ecommerce.data.local.helperEntities.home.TopCategoryEntity;
import com.intcore.internship.ecommerce.data.models.CategoryModel;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import static com.intcore.internship.ecommerce.data.local.TablesNames.CATEGORIES;
import static com.intcore.internship.ecommerce.data.local.TablesNames.TOP_CATEGORIES;


@Dao
public interface TopCategoriesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTopCategories(List<TopCategoryEntity> topCategoryEntityList);

    @Query("SELECT * from " + CATEGORIES + " INNER JOIN "+TOP_CATEGORIES+" ON "+TOP_CATEGORIES + ".category_id=" + CATEGORIES + ".id")
    List<CategoryModel> getTopCategories();

    @Query("DELETE FROM "+TOP_CATEGORIES)
    void deleteAll();

}
