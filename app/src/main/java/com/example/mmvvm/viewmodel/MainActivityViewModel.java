package com.example.mmvvm.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.mmvvm.model.Category;
import com.example.mmvvm.model.Course;
import com.example.mmvvm.model.CourseShopRepository;

import java.util.List;

public class MainActivityViewModel extends ViewModel {

   private final CourseShopRepository repository;

    public MainActivityViewModel(CourseShopRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<Category>> getAllCategories(){
        return repository.getCategories();
    }
    public LiveData<List<Course>>getCoursesOfSelectedCategory(int category_id){
        return repository.getCourses(category_id);
    }
    public void addNewCourse(Course course){
        repository.insertCourse(course);
    }
    public void updateCourse(Course course){
        repository.updateCourse(course);
    }
    public void deleteCourse(Course course){repository.deleteCourse(course);
    }
}
