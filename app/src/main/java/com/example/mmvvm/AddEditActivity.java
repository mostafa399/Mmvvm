package com.example.mmvvm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.mmvvm.databinding.ActivityAddEditBinding;
import com.example.mmvvm.model.Course;

public class AddEditActivity extends AppCompatActivity {
    private final Course course= new Course();
    public static final String COURSE_ID="CourseId";
    public static final String COURSE_NAME="CourseName";
    public static final String COURSE_PRICE="CoursePrice";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        ActivityAddEditBinding activityAddEditBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_edit);
        activityAddEditBinding.setMCourse(course);

        AddEditClickHandler mClickHandler = new AddEditClickHandler(this);
        activityAddEditBinding.setMClickHandler(mClickHandler);


        Intent i = getIntent();
        if (i.hasExtra(COURSE_ID)) {
            // RecyclerView item Clicked

            setTitle("Edit Course");
            course.setCourseName(i.getStringExtra(COURSE_NAME));
            course.setUnitPrice(i.getStringExtra(COURSE_PRICE));

        }
        else {
            // FAB is Clicked
            setTitle("Create New Course");
        }

    }

    public class AddEditClickHandler{
        Context context;

        public AddEditClickHandler(Context context) {
            this.context = context;
        }
        public void onSubmit(View view){
            if (course.getCourseName() == null){
                Toast.makeText(context, "Course Name is Empty", Toast.LENGTH_SHORT).show();
            }else{
                Intent i = new Intent();
                i.putExtra(COURSE_NAME, course.getCourseName());
                i.putExtra(COURSE_PRICE, course.getUnitPrice());
                setResult(RESULT_OK, i);
                Log.v("TAG", course.getCourseName());
                finish();
            }

        }
    }
}