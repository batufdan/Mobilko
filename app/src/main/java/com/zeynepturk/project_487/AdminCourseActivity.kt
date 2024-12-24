package com.zeynepturk.project_487

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zeynepturk.project_487.adapter.CustomAdminCourseRecyclerViewAdapter
import com.zeynepturk.project_487.databinding.ActivityAdminCourseBinding
import com.zeynepturk.project_487.databinding.DialogCourseAddBinding
import com.zeynepturk.project_487.databinding.DialogCourseUpdateBinding
import com.zeynepturk.project_487.db.AdminCourseViewModel
import com.zeynepturk.project_487.model.Admin
import com.zeynepturk.project_487.model.Courses
import com.zeynepturk.project_487.model.Student
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Collections

class AdminCourseActivity : AppCompatActivity() {
    lateinit var bindingAdminCourse: ActivityAdminCourseBinding
    lateinit var courseAdapter: CustomAdminCourseRecyclerViewAdapter
    lateinit var courseViewModel: AdminCourseViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        bindingAdminCourse = ActivityAdminCourseBinding.inflate(layoutInflater)
        setContentView(bindingAdminCourse.root)

        courseAdapter = CustomAdminCourseRecyclerViewAdapter(this, mutableListOf()) { course ->
            showUpdateInstructorDialog(course)
        }
        bindingAdminCourse.courseList.layoutManager = LinearLayoutManager(this)
        bindingAdminCourse.courseList.adapter = courseAdapter

        courseViewModel = ViewModelProvider(this).get(AdminCourseViewModel::class.java)

        prepareData()


        courseViewModel.readAllData.observe(this) { courses ->
            courseAdapter.setData(courses)
        }




        bindingAdminCourse.search.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(key: Editable?) {
                filterCourses(key.toString())            }

        })


        bindingAdminCourse.addCourseBtn.setOnClickListener {
            showAddCourseDialog()
        }

        swipeToDelete()


    }

    private fun swipeToDelete() {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val course = courseAdapter.getCourseAt(position)

                courseViewModel.deleteCourse(course) { rowsDeleted ->
                    if (rowsDeleted > 0) {
                        Toast.makeText(this@AdminCourseActivity, "Course deleted", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@AdminCourseActivity, "Error deleting course", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(bindingAdminCourse.courseList)
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

    private fun filterCourses(query: String) {
        courseViewModel.readAllData.observe(this) { courses ->
            if (query.isEmpty()) {
                courseAdapter.setData(courses)
            } else {
                val filteredCourses = courses.filter {
                    it.courseName.contains(query, ignoreCase = true) ||
                            it.courseCode.contains(query, ignoreCase = true) ||
                            it.instructorName.contains(query, ignoreCase = true)
                }
                courseAdapter.setData(filteredCourses)
            }
        }
    }

    private fun showUpdateInstructorDialog(course: Courses) {
        val dialogBinding = DialogCourseUpdateBinding.inflate(layoutInflater)
        val dialog = Dialog(this)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.instName.setText(course.instructorName)

        dialogBinding.tvcourseName.text = course.courseName

        dialogBinding.save.setOnClickListener {
            val newInstructorName = dialogBinding.instName.text.toString()

            if (newInstructorName.isEmpty()) {
                Toast.makeText(this, "Instructor name cannot be empty!", Toast.LENGTH_SHORT).show()
            } else {
                val updatedCourse = Courses(
                    courseCode = course.courseCode,
                    courseName = course.courseName,
                    instructorName = newInstructorName
                )

                courseViewModel.updateCourse(updatedCourse) { updatedRecords ->
                    if (updatedRecords > 0) {
                        Toast.makeText(this, "Instructor updated successfully!", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    } else {
                        Toast.makeText(this, "Failed to update instructor!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        dialogBinding.close.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }



    fun prepareData() {
        val initialCourses = listOf(
            Courses("CTIS-487", "Mobile Application Development", "Leyla Sezer"),
            Courses("CTIS-359", "Software Engineering Principles", "Cüneyt Sevgi"),
            Courses("MATH-220", "Linear Algebra", "Ahmet Muhtar Güloğlu"),
            Courses("COMD-358", "Professional Communication", "Deniz Erbil"),
            Courses("CTIS-365", "Applied Data Analysis", "Seyid Amjad")
        )
        courseViewModel.addCourses(initialCourses)
    }

}