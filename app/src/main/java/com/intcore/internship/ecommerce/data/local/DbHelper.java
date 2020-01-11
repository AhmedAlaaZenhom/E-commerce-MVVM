package com.intcore.internship.ecommerce.data.local;

import android.content.Context;
import android.util.Log;

import com.intcore.internship.ecommerce.data.remote.helperModels.home.HomeResponseModel;

import java.util.ArrayList;

import androidx.room.Room;
import io.reactivex.Observable;

public class DbHelper {

    private static final String TAG = DbHelper.class.getSimpleName();

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

    public void insertHomeData(HomeResponseModel homeResponseModel) {
        Log.d(TAG,"insertHomeData");
        if(homeResponseModel==null)
            return;
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

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public Observable<HomeResponseModel> getHomeLocalData(){
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
