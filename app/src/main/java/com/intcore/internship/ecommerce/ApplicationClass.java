package com.intcore.internship.ecommerce;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.intcore.internship.ecommerce.data.sharedPreferences.PreferenceHelper;
import com.intcore.internship.ecommerce.di.CompositionRoot;
import com.yariksoffice.lingver.Lingver;

public class ApplicationClass extends Application {
    private CompositionRoot compositionRoot ;

    @Override
    public void onCreate() {
        super.onCreate();
        Lingver.init(this, PreferenceHelper.LOCALE_ENGLISH);

        compositionRoot = new CompositionRoot(this);
    }

    public CompositionRoot getCompositionRoot() {
        return compositionRoot;
    }


}
