package com.intcore.internship.ecommerce.data.local.helperEntities.home;

import com.intcore.internship.ecommerce.data.local.TablesNames;
import com.intcore.internship.ecommerce.data.models.ProductModel;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = TablesNames.BEST_SELLER)

public class BestSellerEntity {

    @PrimaryKey
    @ColumnInfo(name = "product_id")
    private Integer productID ;

    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

}
