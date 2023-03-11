package com.example.mmvvm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mmvvm.CourseDiffCallback;
import com.example.mmvvm.R;
import com.example.mmvvm.databinding.CourseItemListBinding;
import com.example.mmvvm.model.Course;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter <MyAdapter.MyViewHolder> {

    private onItemClickListener listener;
    Context context;
    private ArrayList<Course>courses;

    public MyAdapter(Context context, ArrayList<Course> courses) {
        this.context=context;
        this.courses = courses;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
    private final CourseItemListBinding courseItemListBinding;
        public MyViewHolder(@NonNull CourseItemListBinding courseItemListBinding) {
            super(courseItemListBinding.getRoot());
            this.courseItemListBinding=courseItemListBinding;

            courseItemListBinding.getRoot().setOnClickListener(v -> {
                int clickedPos =getAdapterPosition();
                if (listener!=null && clickedPos!=RecyclerView.NO_POSITION){
                    listener.onItemClick(courses.get(clickedPos));
                }

            });
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CourseItemListBinding courseItemListBinding=DataBindingUtil.inflate(LayoutInflater.
                from(parent.getContext()), R.layout.course_item_list,parent,false);
        return new MyViewHolder(courseItemListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Course course=courses.get(position);
        holder.courseItemListBinding.setCourse(course);
    }

    @Override
    public int getItemCount() {
        return null!=courses?courses.size():0;
    }


    public interface onItemClickListener{
         void onItemClick(Course course);
    }


    public void setListener(onItemClickListener listener){
        this.listener=listener;

    }

    public void setCourses(ArrayList<Course> newCourses) {
        final DiffUtil.DiffResult result=DiffUtil.calculateDiff(new CourseDiffCallback(courses,newCourses),false);
        courses=newCourses;
        result.dispatchUpdatesTo(MyAdapter.this);
    }
}
