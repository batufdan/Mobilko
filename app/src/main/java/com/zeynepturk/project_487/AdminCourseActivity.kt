package com.zeynepturk.project_487

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zeynepturk.project_487.adapter.CustomAdminCourseRecyclerViewAdapter
import com.zeynepturk.project_487.databinding.ActivityAdminCourseBinding
import com.zeynepturk.project_487.databinding.DialogCourseAddBinding
import com.zeynepturk.project_487.db.AdminCourseViewModel
import com.zeynepturk.project_487.model.Courses

class AdminCourseActivity : AppCompatActivity() {
    lateinit var bindingAdminCourse: ActivityAdminCourseBinding
    private val courseList = mutableListOf<Courses>()
    private lateinit var courseAdapter: CustomAdminCourseRecyclerViewAdapter
    private lateinit var courseViewModel: AdminCourseViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        bindingAdminCourse = ActivityAdminCourseBinding.inflate(layoutInflater)
        setContentView(bindingAdminCourse.root)

        /*

        courseAdapter = CustomAdminCourseRecyclerViewAdapter(this)
        bindingAdminCourse.courseList.setLayoutManager(LinearLayoutManager(this))
        bindingAdminCourse.courseList.adapter = courseAdapter

         */


        //courseViewModel = ViewModelProvider(this).get(AdminCourseViewModel::class.java)


        /*
        courseViewModel.readAllData.observe(this) { courses ->
            courseAdapter.setData(courses)
        }

         */





        /*

        bindingAdminCourse.search.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(key: Editable?) {
                filterCourses(key.toString())            }

        })

         */


        bindingAdminCourse.addCourseBtn.setOnClickListener {
            showAddCourseDialog()
        }



    }



    private fun showAddCourseDialog(){
        val dialogBinding = DialogCourseAddBinding.inflate(layoutInflater)
        val dialog = Dialog(this)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.btnAdd.setOnClickListener {
            val courseName = dialogBinding.courseNameEdit.text.toString()
            val courseCode = dialogBinding.courseCodeEdit.text.toString()
            val instructorName = dialogBinding.instructorEdit.text.toString()

            if (courseName.isEmpty() || courseCode.isEmpty() || instructorName.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields!", Toast.LENGTH_SHORT).show()
            } else {
                val newCourse = Courses(courseCode, courseName, instructorName)
                courseViewModel.addCourse(newCourse)
                courseViewModel.readAllData.observe(this) { courses ->
                    courseAdapter.setData(courses)
                }

                Toast.makeText(this, "Course added successfully!", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        }

        dialogBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

}