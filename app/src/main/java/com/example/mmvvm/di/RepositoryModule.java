package com.example.mmvvm.di;

import android.app.Application;

import com.example.mmvvm.model.CourseShopRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {
    @Singleton
    @Provides
    CourseShopRepository provideCourseShopRepository(Application application){
        return new CourseShopRepository(application);

    }

}
