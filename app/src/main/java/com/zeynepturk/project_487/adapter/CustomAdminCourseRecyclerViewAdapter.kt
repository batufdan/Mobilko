package com.zeynepturk.project_487.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.zeynepturk.project_487.R
import com.zeynepturk.project_487.model.Courses

class CustomAdminCourseRecyclerViewAdapter(private val context: Context, private val courses: MutableList<Courses> = mutableListOf(),  private val onEditClicked: (Courses) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    fun setData(newCourses: List<Courses>) {
        courses.clear()
        courses.addAll(newCourses)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val inflator = LayoutInflater.from(parent.context)
        val itemView: View = inflator.inflate(R.layout.item_course, parent, false)
        return CourseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val course = courses[position]
        if (holder is CourseViewHolder) {
            holder.courseCode.text = course.courseCode
            holder.courseName.text = course.courseName
            holder.courseInstructor.text = course.instructorName

            holder.fabUpdate.setOnClickListener {
                onEditClicked(course)
            }
        }
    }


    override fun getItemCount(): Int = courses.size


    inner class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val courseCode: TextView = itemView.findViewById(R.id.courseCode)
        val courseName: TextView = itemView.findViewById(R.id.tvcourseName)
        val courseInstructor: TextView = itemView.findViewById(R.id.tvInstructor)
        val fabUpdate: FloatingActionButton = itemView.findViewById(R.id.fabUpdate)
    }

    fun addCourse(course: Courses) {
        courses.add(course)
        notifyItemInserted(courses.size - 1)
    }


    fun updateCourse(course: Courses) {
        val index = courses.indexOfFirst { it.courseCode == course.courseCode }
        if (index != -1) {
            courses[index] = course
            notifyItemChanged(index)
        }
    }

}