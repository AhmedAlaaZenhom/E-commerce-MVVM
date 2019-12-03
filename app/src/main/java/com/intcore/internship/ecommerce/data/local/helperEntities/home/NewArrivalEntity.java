package com.intcore.internship.ecommerce.data.local.helperEntities.home;

import com.intcore.internship.ecommerce.data.local.TablesNames;
import com.intcore.internship.ecommerce.data.models.ProductModel;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = TablesNames.NEW_ARRIVALS)
public class NewArrivalEntity {

    @PrimaryKey
    @ColumnInfo(name = "product_id")
    private Integer productID ;

    public NewArrivalEntity() {

    }
    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

}
