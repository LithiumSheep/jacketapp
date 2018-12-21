package com.lithiumsheep.jacketapp.arch.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public class AppViewModelFactory implements ViewModelProvider.Factory  {

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        // TODO:
        return null;
    }
}
