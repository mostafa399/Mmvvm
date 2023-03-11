package com.example.mmvvm.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.mmvvm.model.CourseShopRepository;

import javax.inject.Singleton;

@Singleton
public class MainViewModelFactoy implements ViewModelProvider.Factory {
    private final CourseShopRepository courseShopRepository;

    public MainViewModelFactoy(CourseShopRepository courseShopRepository) {
        this.courseShopRepository = courseShopRepository;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainActivityViewModel(courseShopRepository);
    }
}
