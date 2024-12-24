package com.zeynepturk.project_487.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.zeynepturk.project_487.R
import com.zeynepturk.project_487.db.InstructorDAO
import com.zeynepturk.project_487.model.Instructor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CustomInstructorRecyclerViewAdapter(private val context: Context,
                                          private val instructors: List<Instructor>,
                                          private val instructorDAO: InstructorDAO,
                                          private val onTakeClick: (Instructor) -> Unit) : RecyclerView.Adapter<CustomInstructorRecyclerViewAdapter.InstructorViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomInstructorRecyclerViewAdapter.InstructorViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_instructor, parent, false)
        return InstructorViewHolder(view)    }

    override fun onBindViewHolder(holder: CustomInstructorRecyclerViewAdapter.InstructorViewHolder, position: Int
    ) {
        val instructor = instructors[position]
        holder.bind(instructor)
    }

    override fun getItemCount(): Int = instructors.size

    inner class InstructorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvInstructorName: TextView = itemView.findViewById(R.id.tvInstructorName)
        private val tvCourseCode: TextView = itemView.findViewById(R.id.tvCourseCode)
        private val tvOfficeHour: TextView = itemView.findViewById(R.id.tvOfficeHour)
        private val btnTake: Button = itemView.findViewById(R.id.btnTake)

        fun bind(instructor: Instructor) {
            tvInstructorName.text = instructor.instructorName
            tvCourseCode.text = "Course Code: ${instructor.instructorID}"
            tvOfficeHour.text = "Office Hour: ${instructor.instructorOfficeHour}"


            val ins = instructorDAO.getInstructorByName(instructor.instructorName)
            Log.d("INSTRUCTOR NAME", ins?.instructorName.toString() + ins?.isTaken)
            Log.d("OLD INST NAME", instructor.instructorName)

            if (ins?.isTaken == "Not Taken") {
                btnTake.text = "Taken"
                btnTake.visibility = View.VISIBLE
                btnTake.setOnClickListener {
                    btnTake.visibility = View.INVISIBLE
                    Log.d("CLICKED", ins.isTaken)
                    onTakeClick(instructor)
                }
            } else {
                btnTake.visibility = View.GONE
            }
        }
    }


}