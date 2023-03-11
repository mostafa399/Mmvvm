package com.example.mmvvm.di;

import com.example.mmvvm.ActivityMain;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class,RepositoryModule.class})
public interface CourseShopComponent {

    void inject(ActivityMain activityMain);

}
