package com.example.mmvvm;

import android.app.Application;

import com.example.mmvvm.di.AppModule;
import com.example.mmvvm.di.CourseShopComponent;
import com.example.mmvvm.di.DaggerCourseShopComponent;

public class App extends Application {
    private static App app;
    private CourseShopComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        app=this;
       component= DaggerCourseShopComponent.builder().appModule(new AppModule(this)).build();

    }

    public static App getApp() {
        return app;
    }

    public CourseShopComponent getComponent() {
        return component;
    }
}
