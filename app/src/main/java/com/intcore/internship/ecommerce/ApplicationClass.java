package com.intcore.internship.ecommerce;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.intcore.internship.ecommerce.di.CompositionRoot;

public class ApplicationClass extends Application {
    private CompositionRoot compositionRoot ;

    @Override
    public void onCreate() {
        super.onCreate();
        compositionRoot = new CompositionRoot(this);
    }

    public CompositionRoot getCompositionRoot() {
        return compositionRoot;
    }


}
