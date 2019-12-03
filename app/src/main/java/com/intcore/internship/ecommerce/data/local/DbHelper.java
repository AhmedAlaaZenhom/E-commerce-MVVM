package com.intcore.internship.ecommerce.data.local;

import android.content.Context;

import com.intcore.internship.ecommerce.data.local.helperEntities.home.BestSellerEntity;

import com.intcore.internship.ecommerce.data.remote.helperModels.home.HomeResponseModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import androidx.room.Room;
import io.reactivex.Observable;
import io.reactivex.Single;

public class DbHelper {

    private Context applicationContext ;

    private ApplicationDatabase applicationDatabase ;

    public DbHelper(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    private ApplicationDatabase getApplicationDatabase() {
        if(applicationDatabase==null)
            applicationDatabase = Room.databaseBuilder(applicationContext, ApplicationDatabase.class, "ApplicationDatabase")
                    .fallbackToDestructiveMigration()
                    .build();
        return applicationDatabase;
    }

    public boolean insertHomeData(HomeResponseModel homeResponseModel) {
        if(homeResponseModel==null)
            return false;
        try {
            getApplicationDatabase().productModelDao().insertProducts(homeResponseModel.getNewArrivalModelList());
            getApplicationDatabase().newArrivalDao().deleteAll();
            getApplicationDatabase().newArrivalDao().insertNewArrivals(homeResponseModel.getNewArrivalsEntityList());

            getApplicationDatabase().productModelDao().insertProducts(homeResponseModel.getBestSellerModelList());
            getApplicationDatabase().bestSellersDao().deleteAll();
            getApplicationDatabase().bestSellersDao().insertBestSellers(homeResponseModel.getBestSellersEntityList());

            getApplicationDatabase().categoryModelDao().insertCategories(homeResponseModel.getTopCategoryModelList());
            getApplicationDatabase().topCategoriesDao().deleteAll();
            getApplicationDatabase().topCategoriesDao().insertTopCategories(homeResponseModel.getTopCategoryEntityList());

            return true ;
        } catch (Exception e){
            return false ;
        }
    }

    public Observable<HomeResponseModel> getHomeData(){
        return Observable.fromCallable(() -> {
            final HomeResponseModel homeResponseModel = new HomeResponseModel();
            homeResponseModel.setNewArrivalModelList(getApplicationDatabase().newArrivalDao().getNewArrivalProducts());
            homeResponseModel.setBestSellerModelList(getApplicationDatabase().bestSellersDao().getBestSellerProducts());
            homeResponseModel.setTopCategoryModelList(getApplicationDatabase().topCategoriesDao().getTopCategories());
            homeResponseModel.setHotDealModelList(new ArrayList<>());
            return homeResponseModel;
        });
    }

}
