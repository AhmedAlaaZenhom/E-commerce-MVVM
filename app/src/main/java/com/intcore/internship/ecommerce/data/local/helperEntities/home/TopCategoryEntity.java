package com.intcore.internship.ecommerce.data.local.helperEntities.home;

import com.intcore.internship.ecommerce.data.local.TablesNames;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = TablesNames.TOP_CATEGORIES)

public class TopCategoryEntity {

    @PrimaryKey
    @ColumnInfo(name = "category_id")
    private Integer categoryID ;

    public Integer getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Integer categoryID) {
        this.categoryID = categoryID;
    }

}
