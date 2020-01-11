package com.intcore.internship.ecommerce.di;

import com.intcore.internship.ecommerce.ApplicationClass;
import com.intcore.internship.ecommerce.ui.commonClasses.ViewModelProviderFactory;
import com.intcore.internship.ecommerce.data.DataManager;

public class CompositionRoot {

    private ApplicationClass application ;

    public CompositionRoot(ApplicationClass application) {
        this.application = application;
    }

    private DataManager dataManager ;
    public DataManager getDataManager() {
        if (dataManager == null)
            dataManager = new DataManager(application);
        return dataManager;
    }

    private ViewModelProviderFactory viewModelProviderFactory ;
    ViewModelProviderFactory getViewModelProviderFactory() {
        if(viewModelProviderFactory==null){
            viewModelProviderFactory = new ViewModelProviderFactory(application) ;
        }
        return  viewModelProviderFactory ;
    }


}
