package com.zeynepturk.project_487

import SwipeToDeleteCallback
import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
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
    // lateinit var filteredList : MutableList<Student>
    lateinit var coursesTakenList: ArrayList<CoursesTaken>
    lateinit var adapter: CustomAdminStuRecyclerViewAdapter
    lateinit var stuViewModel: AdminStuViewModel
    lateinit var students :ArrayList<Student>
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

        //DELETE
        adapter = CustomAdminStuRecyclerViewAdapter(this, images) { stu ->
            stuViewModel.studentDAO.deleteStudent(stu)
        }

        bindingAdminStu.stuList.adapter = adapter
        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(adapter))
        itemTouchHelper.attachToRecyclerView(bindingAdminStu.stuList)

        getData()
        bindingAdminStu.search.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    // searchStu(s.toString())
                }
        })

        bindingAdminStu.addStuBtn.setOnClickListener {
            addItem()
        }
    }

    private fun getData(){
        stuViewModel.readAllData.observe(this) { students ->
            adapter.setData(students)
        }
    }

    private fun addItem(){
        createDialogAdd()
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
            var s = Student(0, pass.text.toString(), name.text.toString(), mail.text.toString(), cgpa.text.toString().toDouble())
            stuViewModel.addStudent(s)
            customDialog.dismiss()
        }
        btnDialogClose.setOnClickListener(View.OnClickListener {
            customDialog.dismiss()
        })
        customDialog.show()
    }

//    fun createDialogUpdate(stuID: Int){
//        var customDialog = Dialog(this)
//        customDialog.setContentView(R.layout.dialog_stu_courses)
//        var btnClose: Button = customDialog.findViewById(R.id.cancel)
//        var btnSave: Button = customDialog.findViewById(R.id.save)
//        var spinner : Spinner = customDialog.findViewById(R.id.spinnerCourses)
//        var attendance: TextView = customDialog.findViewById(R.id.attEdit)
//        var grade: TextView = customDialog.findViewById(R.id.gradeEdit)
//
//        btnSave.setOnClickListener {
//            var taken = ArrayList<CoursesTaken>()
//            taken = mobilkoDB.coursesTakenDao().getAllTakenById(stuID)
//            val courseCodes: List<String> = taken.map { it.coursesCode }
//
//            var adapter = ArrayAdapter<CoursesTaken>(this, R.layout.spinner_items, items)
//            spinner.adapter = adapter
//            var selectedCourse = ""
//            spinner.setOnItemSelectedListener(object: AdapterView.OnItemSelectedListener{
//                override fun onItemSelected(
//                    parent: AdapterView<*>?,
//                    view: View?,
//                    position: Int,
//                    id: Long
//                ) {
//                    selectedCourse = spinner.getSelectedItem().toString()
//                }
//                override fun onNothingSelected(parent: AdapterView<*>?) {
//                    TODO("Not yet implemented")
//                }
//            })
//
//
//        }
//    }

//    private fun searchStu(s : String) {
//        stuViewModel.readAllData.value?.let { students ->
//            for (student in students) {
//                if (student.name.contains(s, ignoreCase = true)) {
//                    filteredList.add(student)
//                }
//            }
//        }
//    }
}