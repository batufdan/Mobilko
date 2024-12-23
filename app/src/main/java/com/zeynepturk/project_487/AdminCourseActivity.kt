package com.zeynepturk.project_487

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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

        courseAdapter = CustomAdminCourseRecyclerViewAdapter(courseList)
        bindingAdminCourse.courseList.apply {
            layoutManager = LinearLayoutManager(this@AdminCourseActivity)
            adapter = courseAdapter
        }


        courseViewModel = ViewModelProvider(this).get(AdminCourseViewModel::class.java)

        bindingAdminCourse.search.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(key: Editable?) {
                filterCourses(key.toString())            }

        })

        bindingAdminCourse.addCourseBtn.setOnClickListener {
            showAddCourseDialog()
        }

    }

    private fun filterCourses(key: String) {
        val filteredList = if (key.isNotEmpty()) {
            courseList.filter { it.courseName.contains(key, ignoreCase = true) }
        } else {
            courseList
        }
        courseAdapter.updateList(filteredList)
    }

    private fun showAddCourseDialog(){
        val dialogBinding = DialogCourseAddBinding.inflate(layoutInflater)
        val dialog = Dialog(this)
        dialog.setContentView(dialogBinding.root)
        dialog.setCancelable(true)

        dialogBinding.btnAdd.setOnClickListener {
            val courseName = dialogBinding.courseNameEdit.text.toString()
            val courseCode = dialogBinding.courseCodeEdit.text.toString()
            val instructor = dialogBinding.instructorEdit.text.toString()

            if (courseName.isNotBlank() && courseCode.isNotBlank() && instructor.isNotBlank()) {
                val newCourse = Courses(courseCode, courseName, instructor)
                courseViewModel.addCourse(newCourse)
                Toast.makeText(this, "Course Added: $courseName", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        dialogBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    //commit
}