package com.thecode007.ecollecter;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.thecode007.ecollecter.network.DataManager;
import com.thecode007.ecollecter.rx.SchedulerProvider;

import org.jetbrains.annotations.NotNull;



public class ViewModelProviderFactory extends ViewModelProvider.NewInstanceFactory {

    private final DataManager dataManager;
    private final SchedulerProvider schedulerProvider;

    public ViewModelProviderFactory(DataManager dataManager,
                                    SchedulerProvider schedulerProvider) {
        this.dataManager = dataManager;
        this.schedulerProvider = schedulerProvider;
    }

    @NotNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {

//       if (modelClass.isAssignableFrom(LoginViewModel.class)) {
//            //noinspection unchecked
//            return (T) new LoginViewModel(dataManager, schedulerProvider);
//        }

        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}