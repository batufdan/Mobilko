package com.zeynepturk.project_487.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zeynepturk.project_487.R
import com.zeynepturk.project_487.model.Courses

class CustomAdminCourseRecyclerViewAdapter(private var courses: List<Courses>, private val onDelete: (Courses) -> Unit) :
    RecyclerView.Adapter<CustomAdminCourseRecyclerViewAdapter.CourseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_course, parent, false)
        return CourseViewHolder(view)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val course = courses[position]
        holder.courseCode.text = course.courseCode
        holder.courseName.text = course.courseName

        holder.deleteButton.setOnClickListener {
            onDelete(course)
        }

        // Set item click listener
        holder.itemView.setOnClickListener {
            Toast.makeText(it.context, "Clicked on ${course.courseName}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = courses.size

    fun addCourse(course: Courses) {
        courses.add(course)
        notifyItemInserted(courses.size - 1)
    }

    // Remove a course from the list
    fun removeCourse(course: Courses) {
        val position = courses.indexOf(course)
        if (position != -1) {
            courses.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    // Update the entire list
    fun updateList(newCourses: List<Courses>) {
        courses.clear()
        courses.addAll(newCourses)
        notifyDataSetChanged()
    }


    inner class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val courseCode: TextView = itemView.findViewById(R.id.courseCode)
        val courseName: TextView = itemView.findViewById(R.id.courseName)
    }
}