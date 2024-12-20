package com.zeynepturk.project_487.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zeynepturk.project_487.databinding.ActivityCourseDetailBinding
import com.zeynepturk.project_487.db.Courses
import com.zeynepturk.project_487.db.CoursesTaken

class CourseDetailAdapter(private val courseDetails: List<Courses>):
    RecyclerView.Adapter<CourseDetailAdapter.CourseDetailViewHolder>()
{

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseDetailViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ActivityCourseDetailBinding.inflate(inflater, parent, false)
            return CourseDetailViewHolder(binding)
        }

        override fun onBindViewHolder(holder: CourseDetailViewHolder, position: Int) {
            val courseDetail = courseDetails[position]
            holder.bind(courseDetail)
        }

        override fun getItemCount(): Int = courseDetails.size

        inner class CourseDetailViewHolder(private val binding: ActivityCourseDetailBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(courseDetail: CoursesTaken) {
                binding.courseCode.text = courseDetail.coursesCode
                binding.courseName.text = courseDetail.stuID.toString()
                binding.attendance.text = courseDetail.attendance.toString()
                binding.grade.text = courseDetail.courseGrade.toString()
            }
        }
    }

