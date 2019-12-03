package com.intcore.internship.ecommerce.di;

import com.intcore.internship.ecommerce.data.DataManager;
import com.intcore.internship.ecommerce.data.sharedPreferences.PreferenceHelper;
import com.intcore.internship.ecommerce.ui.commonClasses.ViewModelProviderFactory;
import com.intcore.internship.ecommerce.ui.commonClasses.ProgressHudHelper;
import com.intcore.internship.ecommerce.ui.commonClasses.ToastsHelper;

import androidx.fragment.app.FragmentActivity;

public class ViewCompositionRoot {

    private CompositionRoot compositionRoot ;
    private FragmentActivity activity ;

    public ViewCompositionRoot(CompositionRoot compositionRoot, FragmentActivity activity) {
        this.compositionRoot = compositionRoot;
        this.activity = activity;
    }

    private CompositionRoot getCompositionRoot() {
        return compositionRoot;
    }

    public FragmentActivity getContext() {
        return activity;
    }

    public ViewModelProviderFactory getViewModelProviderFactory(){
        return getCompositionRoot().getViewModelProviderFactory() ;
    }

    private ToastsHelper toastsHelper ;
    public ToastsHelper getToastsHelper(){
        if(toastsHelper==null)
            toastsHelper = new ToastsHelper(getContext()) ;
        return toastsHelper ;
    }

    private ProgressHudHelper progressHudHelper ;
    public ProgressHudHelper getProgressHudHelper(){
        if(progressHudHelper==null)
            progressHudHelper = new ProgressHudHelper(getContext()) ;
        return progressHudHelper ;
    }

    public DataManager getDataManager(){
        return getCompositionRoot().getDataManager();
    }

}
