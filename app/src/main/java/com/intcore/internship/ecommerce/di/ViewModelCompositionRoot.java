package com.intcore.internship.ecommerce.di;

import com.intcore.internship.ecommerce.data.DataManager;

public class ViewModelCompositionRoot {

    private CompositionRoot compositionRoot ;

    public ViewModelCompositionRoot(CompositionRoot compositionRoot) {
        this.compositionRoot = compositionRoot ;
    }

    public DataManager getDataManager(){
        return compositionRoot.getDataManager() ;
    }
}
