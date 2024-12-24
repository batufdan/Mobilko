package com.zeynepturk.project_487

import SwipeToDeleteCallback
import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.zeynepturk.project_487.adapter.CustomAdminStuRecyclerViewAdapter
import com.zeynepturk.project_487.databinding.ActivityAdminStuBinding
import com.zeynepturk.project_487.db.AdminStuViewModel
import com.zeynepturk.project_487.db.MobilkoRoomDatabase
import com.zeynepturk.project_487.model.CoursesTaken
import com.zeynepturk.project_487.model.Student

class AdminStuActivity : AppCompatActivity() {
    lateinit var bindingAdminStu: ActivityAdminStuBinding
    lateinit var adapter: CustomAdminStuRecyclerViewAdapter
    lateinit var stuViewModel: AdminStuViewModel
    lateinit var students: ArrayList<Student>
    private val mobilkoDB: MobilkoRoomDatabase by lazy {
        Room.databaseBuilder(this, MobilkoRoomDatabase::class.java, "MobilkoDB")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    private val images = listOf(
        R.drawable.kiz1,
        R.drawable.kiz2,
        R.drawable.erkek,
        R.drawable.kiz3
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        bindingAdminStu = ActivityAdminStuBinding.inflate(layoutInflater)
        setContentView(bindingAdminStu.root)

        students = ArrayList<Student>()
        bindingAdminStu.stuList.layoutManager = LinearLayoutManager(this)
        stuViewModel = ViewModelProvider(this).get(AdminStuViewModel::class.java)

        adapter = CustomAdminStuRecyclerViewAdapter(this, images,
            onItemDelete = {student ->
                stuViewModel.studentDAO.deleteStudent(student)
            },
            onEditClicked = {student -> updateTaken(student.id) })


        bindingAdminStu.stuList.adapter = adapter
        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(adapter))
        itemTouchHelper.attachToRecyclerView(bindingAdminStu.stuList)

        getData()
        bindingAdminStu.search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                filterStudents(s.toString())
            }
        })

        bindingAdminStu.addStuBtn.setOnClickListener {
            addItem()
        }
    }

    private fun getData() {
        stuViewModel.readAllData.observe(this) { students ->
            adapter.setData(students)
        }
    }

    private fun addItem() {
        createDialogAdd()
    }

    private fun updateTaken(stuID: Int){
        createDialogUpdate(stuID)
    }

    fun createDialogAdd() {
        var customDialog = Dialog(this)
        customDialog.setContentView(R.layout.dialog_add_stu)
        var btnDialogClose: Button = customDialog.findViewById(R.id.btnCancel)
        var name: TextView = customDialog.findViewById(R.id.stuNameEdit)
        var pass: TextView = customDialog.findViewById(R.id.stuPassEdit)
        var mail: TextView = customDialog.findViewById(R.id.stuMailEdit)
        var cgpa: TextView = customDialog.findViewById(R.id.stuCgpaEdit)
        var btnAdd: Button = customDialog.findViewById(R.id.btnAdd)
        btnAdd.setOnClickListener {
            var s = Student(
                0,
                pass.text.toString(),
                name.text.toString(),
                mail.text.toString(),
                cgpa.text.toString().toDouble()
            )
            stuViewModel.addStudent(s)
            customDialog.dismiss()
        }
        btnDialogClose.setOnClickListener(View.OnClickListener {
            customDialog.dismiss()
        })
        customDialog.show()
    }

    fun createDialogUpdate(stuID: Int) {
        var customDialog = Dialog(this)
        customDialog.setContentView(R.layout.dialog_stu_courses)
        var btnClose: Button = customDialog.findViewById(R.id.cancel)
        var btnSave: Button = customDialog.findViewById(R.id.saveBtn)
        var spinner: Spinner = customDialog.findViewById(R.id.spinnerCourses)
        var attendance: TextView = customDialog.findViewById(R.id.attEdit)
        var grade: TextView = customDialog.findViewById(R.id.gradeEdit)
        var selectedCourse = ""
        val taken: LiveData<List<CoursesTaken>> =
            mobilkoDB.coursesTakenDao().getAllTakenById(stuID)

        taken.observe(this, Observer { coursesTakenList ->
            if (coursesTakenList != null) {
                val courseCodes: ArrayList<String> =
                    ArrayList(coursesTakenList.map { it.coursesCode })

                val adapter =
                    ArrayAdapter(this, android.R.layout.simple_spinner_item, courseCodes)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            }
        })

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedCourse = parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        btnSave.setOnClickListener {
            val coursesTaken = CoursesTaken(
                selectedCourse,
                stuID,
                attendance.text.toString().toInt(),
                grade.text.toString().toInt()
            )
            mobilkoDB.coursesTakenDao().updateTaken(coursesTaken)
            Toast.makeText(this, "$selectedCourse is updated for $stuID with att = ${attendance.text} and grade = ${grade.text}", Toast.LENGTH_SHORT).show()
            customDialog.dismiss()
        }

        btnClose.setOnClickListener { customDialog.dismiss() }

        customDialog.show()
    }

    private fun filterStudents(query: String) {
        stuViewModel.readAllData.observe(this) { students ->
            if (query.isEmpty()) {
                adapter.setData(students)
            } else {
                var filteredCourses = students.filter {
                    it.name.contains(query, ignoreCase = true)
                }
                adapter.setData(filteredCourses as MutableList<Student>)
            }
        }
    }
}