package com.example.mmvvm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mmvvm.adapter.MyAdapter;
import com.example.mmvvm.databinding.ActivityMainMainBinding;
import com.example.mmvvm.model.Category;
import com.example.mmvvm.model.Course;
import com.example.mmvvm.viewmodel.MainActivityViewModel;
import com.example.mmvvm.viewmodel.MainViewModelFactoy;

import java.util.ArrayList;

import javax.inject.Inject;

public class ActivityMain extends AppCompatActivity {
    private MainActivityViewModel mainActivityViewModel;
    private ArrayList<Course>courseList;
     private ActivityMainMainBinding activityMainBinding;
    private static final int ADD_COURSE_REQUEST_CODE=1;
    private static final int EDIT_COURSE_REQUEST_CODE=2;
    private int selectedCourseId;
    private int selectedCategoryId;
    private ArrayList<Category>categoryList;
    private Category selectedCategory;
    @Inject
    public MainViewModelFactoy mainViewModelFactoy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_main);


        //DATA BINDING DECLARATION
        activityMainBinding= DataBindingUtil.setContentView(this,R.layout.activity_main_main);
        MainActivityClickHandler mSecondaryClickHandler = new MainActivityClickHandler(this);
        activityMainBinding.setHandler(mSecondaryClickHandler);


        App.getApp().getComponent().inject(this);
        mainActivityViewModel= new ViewModelProvider(this,mainViewModelFactoy).get(MainActivityViewModel.class);

        //ViewModel Declaration
        mainActivityViewModel=new ViewModelProvider(this).get(MainActivityViewModel.class);
        mainActivityViewModel.getAllCategories().observe(this, categories -> {
            categoryList= (ArrayList<Category>) categories;
            for (Category c:categories){
                Log.i("TAG",c.getCategoryName());
            }
            showSpinner();

        });

 }


    private void showSpinner(){
        ArrayAdapter<Category>categoryArrayAdapter=new ArrayAdapter<>(this,R.layout.spiner_item,categoryList);
        categoryArrayAdapter.setDropDownViewResource(R.layout.spiner_item);
        //viewBinding
        activityMainBinding.setSpinnerAdapter(categoryArrayAdapter);
        }
  private void loadCoursesArrayList(int categoryId){
      mainActivityViewModel.getCoursesOfSelectedCategory(categoryId).observe(this, courses -> {
            courseList= (ArrayList<Course>) courses;
            loadRecyclerView();

        });
  }
  private void loadRecyclerView() {
      RecyclerView recyclerView = activityMainBinding.recyclerView;
      recyclerView.setLayoutManager(new LinearLayoutManager(this));
      recyclerView.setHasFixedSize(true);
      MyAdapter adapter = new MyAdapter(this, courseList);
      recyclerView.setAdapter(adapter);
      adapter.setCourses(courseList);

      //EditCourse if user clicked any item of the recyclerView
      adapter.setListener(course -> {
          selectedCourseId = course.getCourseId();

          Intent editIntent = new Intent(ActivityMain.this, AddEditActivity.class);
          editIntent.putExtra(AddEditActivity.COURSE_ID, selectedCourseId);
          editIntent.putExtra(AddEditActivity.COURSE_NAME, course.getCourseName());
          editIntent.putExtra(AddEditActivity.COURSE_PRICE, course.getUnitPrice());
          startActivityForResult(editIntent, EDIT_COURSE_REQUEST_CODE);
      });
        //Delete by Swipping
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Course course=courseList.get(viewHolder.getAdapterPosition());
                mainActivityViewModel.deleteCourse(course);

            }
        }).attachToRecyclerView(recyclerView);
  }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        selectedCategoryId = selectedCategory.getId();
        if (requestCode == ADD_COURSE_REQUEST_CODE && resultCode == RESULT_OK){
            Course course = new Course();

            course.setCategoryId(selectedCategoryId);
            course.setCourseName(data.getStringExtra(AddEditActivity.COURSE_NAME));
            course.setUnitPrice(data.getStringExtra(AddEditActivity.COURSE_PRICE));
            mainActivityViewModel.addNewCourse(course);
            Log.v("TAG", "Inserted"+course.getUnitPrice());
        }
        else if(requestCode == EDIT_COURSE_REQUEST_CODE && resultCode == RESULT_OK){

            Course course = new Course();
            course.setCategoryId(selectedCategoryId);
            course.setCourseName(data.getStringExtra(AddEditActivity.COURSE_NAME));
            course.setUnitPrice(data.getStringExtra(AddEditActivity.COURSE_PRICE));
            course.setCourseId(selectedCourseId);
            mainActivityViewModel.updateCourse(course);

        }
    }


    public class MainActivityClickHandler{
        Context context;

        public MainActivityClickHandler(Context context) {
            this.context = context;
        }
        public void OnFabClickHandler(View view){
            //CreateCourse

            Intent intent=new Intent(ActivityMain.this,AddEditActivity.class);
            startActivityForResult(intent,ADD_COURSE_REQUEST_CODE);


        }

        public void onSelectItem(AdapterView<?>adapterView,View view,int pos,long id){
            selectedCategory= (Category) adapterView.getItemAtPosition(pos);
            loadCoursesArrayList(selectedCategory.getId());

        }
    }


}