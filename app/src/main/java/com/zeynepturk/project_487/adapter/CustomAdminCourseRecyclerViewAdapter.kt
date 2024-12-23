package com.zeynepturk.project_487.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zeynepturk.project_487.R
import com.zeynepturk.project_487.model.Courses

class CustomAdminCourseRecyclerViewAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var recyclerItemValues = emptyList<Courses>()
    private var allCourses = mutableListOf<Courses>()

    fun setData(items: List<Courses>) {
        recyclerItemValues = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val inflator = LayoutInflater.from(parent.context)
        val itemView: View = inflator.inflate(R.layout.item_course, parent, false)
        return CourseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val course = recyclerItemValues[position]
        if (holder is CourseViewHolder) { // Cast holder to CourseViewHolder
            holder.courseCode.text = course.courseCode
            holder.courseName.text = course.courseName
        }
    }


    override fun getItemCount(): Int = recyclerItemValues.size


    inner class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val courseCode: TextView = itemView.findViewById(R.id.courseCode)
        val courseName: TextView = itemView.findViewById(R.id.courseName)
    }

    fun addCourse(course: Courses) {
        allCourses.add(course)
        notifyItemInserted(allCourses.size - 1)
    }

    fun updateList(newCourses: List<Courses>) {
        allCourses.clear() // Clear the existing list
        allCourses.addAll(newCourses) // Add all items from the new list
        notifyDataSetChanged() // Notify the adapter about data changes
    }


    fun updateCourse(course: Courses) {
        val index = allCourses.indexOfFirst { it.courseName == course.courseName }
        if (index != -1) {
            allCourses[index] = course
            notifyItemChanged(index)
        }
    }

}