package com.example.mmvvm.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CourseShopRepository {
    private final CategoryDao categoryDao;
    private final CourseDao courseDao;
    private LiveData<List<Category>> categories;
    private LiveData<List<Course>>courses;
     ExecutorService executor= Executors.newSingleThreadExecutor();
    public CourseShopRepository(Application application) {
        CourseDatabase courseDatabase=CourseDatabase.getInstance(application);
        categoryDao= courseDatabase.categoryDao();
        courseDao=courseDatabase.courseDao();
    }
    public LiveData<List<Category>> getCategories() {return categoryDao.getAllCategories();}

    public LiveData<List<Course>> getCourses(int category_Id) {return courseDao.getCourses(category_Id);}

    public void insertCategory(Category c){
        executor.execute(()->categoryDao.insert(c));
    }
    public void deleteCategory(Category c){executor.execute(() -> categoryDao.delete(c));}
    public void updateCategory(Category c){
        executor.execute(() -> categoryDao.update(c));
    }

    public void insertCourse(Course c){
        executor.execute(() -> courseDao.insert(c));
    }

    public void deleteCourse(Course c){executor.execute(() -> courseDao.delete(c));}
    public void updateCourse(Course c){executor.execute(() -> courseDao.update(c));}

}
