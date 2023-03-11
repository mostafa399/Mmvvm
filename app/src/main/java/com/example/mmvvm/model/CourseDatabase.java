package com.example.mmvvm.model;

import static java.util.concurrent.Executors.newSingleThreadExecutor;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;

@Database(entities = {Category.class,Course.class},version = 1)
    public abstract class  CourseDatabase extends RoomDatabase {
    public abstract CourseDao courseDao();
    public abstract CategoryDao categoryDao();
    private static CourseDatabase instance;

    public static synchronized CourseDatabase getInstance(Context c){
        if (instance==null){
            instance= Room.databaseBuilder(c.getApplicationContext(), CourseDatabase.class, "courseDB")
                            .fallbackToDestructiveMigration()
                                    .addCallback(roomCallback)
                                            .build();
        }
        return instance;
    }

   private static final RoomDatabase.Callback roomCallback=new RoomDatabase.Callback(){
       @Override
       public void onCreate(@NonNull SupportSQLiteDatabase db) {
           super.onCreate(db);
           initializeData();
       }
   };

    private static void initializeData(){
        ExecutorService executorService= newSingleThreadExecutor();
        executorService.execute(() -> {
            Category category1=new Category();
            category1.setCategoryName("FrontEnd");
            category1.setCategoryDescription("Web Development Interface");


            Category category2=new Category();
            category2.setCategoryName("BackEnd");
            category2.setCategoryDescription("Web Development Database");

            instance.categoryDao().insert(category1);
            instance.categoryDao().insert(category2);

            Course course1=new Course();
            course1.setCourseName("Html");
            course1.setUnitPrice("100$");
            course1.setCategoryId(1);

            Course course2=new Course();
            course2.setCourseName("Css");
            course2.setUnitPrice("200$");
            course2.setCategoryId(1);


            Course course3=new Course();
            course3.setCourseName("Js");
            course3.setUnitPrice("500$");
            course3.setCategoryId(2);

            Course course4=new Course();
            course4.setCourseName("React");
            course4.setUnitPrice("500$");
            course4.setCategoryId(2);


            instance.courseDao().insert(course1);
            instance.courseDao().insert(course2);
            instance.courseDao().insert(course3);
            instance.courseDao().insert(course4);





        });

    }

}
