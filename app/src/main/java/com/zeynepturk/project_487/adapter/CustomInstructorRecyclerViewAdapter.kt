package com.zeynepturk.project_487.adapter

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
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
        holder.bind(instructor, position)
    }

    override fun getItemCount(): Int = instructors.size

    inner class InstructorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvInstructorName: TextView = itemView.findViewById(R.id.tvInstructorName)
        private val tvCourseCode: TextView = itemView.findViewById(R.id.tvCourseCode)
        private val tvOfficeHour: TextView = itemView.findViewById(R.id.tvOfficeHour)
        private val btnTake: Button = itemView.findViewById(R.id.btnTake)
        private val img: ImageView = itemView.findViewById(R.id.imageView2)

        fun bind(instructor: Instructor, position: Int) {
            tvInstructorName.text = instructor.instructorName
            tvCourseCode.text = "Course Code: ${instructor.instructorID}"
            tvOfficeHour.text = "Office Hour: ${instructor.instructorOfficeHour}"
            img.setImageResource(instructor.img)

            val ins = instructorDAO.getInstructorByName(instructor.instructorName)
            Log.d("INSTRUCTOR NAME", ins?.instructorName.toString() + ins?.isTaken)
            Log.d("OLD INST NAME", instructor.instructorName)

            //Deneme için
            if (ins?.isTaken == "Not Taken") {
                btnTake.text = "Taken"
                btnTake.visibility = View.VISIBLE
                val mediaPlayer = MediaPlayer.create(context, R.raw.boom_sound_effect)
                btnTake.setOnClickListener {
                    mediaPlayer.start()
                    Log.d("CLICKED", ins.isTaken)
                    onTakeClick(instructor)
                    YoYo.with(Techniques.FadeOut)
                        .duration(700) // Duration of the animation in milliseconds
                        .onEnd { btnTake.visibility = View.GONE } // Hide the button after the animation
                        .playOn(btnTake)
                }
                mediaPlayer.setOnCompletionListener {
                    mediaPlayer.release()
                }
            } else {
                btnTake.visibility = View.GONE
            }
        }
    }


}