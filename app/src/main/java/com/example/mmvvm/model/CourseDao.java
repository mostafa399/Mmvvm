package com.example.mmvvm.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CourseDao {
    @Insert
    void insert(Course course);
    @Update
    void update(Course course);
    @Delete
    void delete(Course course);
    @Query("select*from course_table")
    LiveData<List<Course>>getAllCourses();
    @Query("select*from course_table where category_id==:cat_id")
    LiveData<List<Course>>getCourses(int cat_id);


}
