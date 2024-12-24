package com.zeynepturk.project_487.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.zeynepturk.project_487.R
import com.zeynepturk.project_487.model.Courses
import com.zeynepturk.project_487.model.Student

class CustomStudentRVAdapter(private val context: Context, private val onItemClicked: (Courses) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var recyclerViewItem = emptyList<Courses>()

    fun setData(items: MutableList<Courses>) {
        recyclerViewItem = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflator = LayoutInflater.from(parent.context)
        val itemView: View = inflator.inflate(R.layout.item_stu_course, parent, false)
        return CourseViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return recyclerViewItem.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = recyclerViewItem[position]
        val itemHolder = holder as CourseViewHolder
        itemHolder.courseCode.text = currentItem.courseCode
        itemHolder.courseName.text = currentItem.courseName
    }

    inner class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val courseCode: TextView = itemView.findViewById(R.id.courseCodeTxt)
        val courseName: TextView = itemView.findViewById(R.id.courseNameTxt)
        val layout: LinearLayout = itemView.findViewById(R.id.linearLayoutStudent)

    }
}