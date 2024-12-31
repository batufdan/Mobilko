package com.zeynepturk.project_487

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.zeynepturk.project_487.adapter.CustomStudentRVAdapter
import com.zeynepturk.project_487.databinding.ActivityStuMainBinding
import com.zeynepturk.project_487.db.AdminStuViewModel
import com.zeynepturk.project_487.db.MobilkoRoomDatabase
import com.zeynepturk.project_487.db.StudentMainViewModel
import com.zeynepturk.project_487.db.StudentMainViewModelFactory
import com.zeynepturk.project_487.model.Courses
import com.zeynepturk.project_487.model.CoursesTaken

class StudentMainActivity : AppCompatActivity() {
    lateinit var bindingStu: ActivityStuMainBinding
    lateinit var taken: LiveData<MutableList<CoursesTaken>>
    lateinit var adapter: CustomStudentRVAdapter
    lateinit var customDialog: Dialog
    lateinit var stuViewModel: StudentMainViewModel
    private val mobilkoDB: MobilkoRoomDatabase by lazy {
        Room.databaseBuilder(this, MobilkoRoomDatabase::class.java, "MobilkoDB")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        bindingStu = ActivityStuMainBinding.inflate(layoutInflater)
        setContentView(bindingStu.root)

        val b = intent.extras
        val stuID = b?.getInt("STUDENT_ID")
        val stu = mobilkoDB.studentDao().getStudentById(stuID!!)

        bindingStu.stuID.text = stuID.toString()
        bindingStu.stuCGPA.text = stu.cgpa.toString()
        bindingStu.stuMail.text = stu.mail
        bindingStu.stuName.text = stu.name

        stuViewModel = ViewModelProvider(this, StudentMainViewModelFactory(application, stuID)).get(StudentMainViewModel::class.java)
        taken = stuViewModel.readAllData

        taken.observe(this, Observer { coursesTakenList ->
            if (coursesTakenList != null) {
                val courseCodes: ArrayList<String> =
                    ArrayList(coursesTakenList.map { it.coursesCode })

                val takenCourses : List<Courses> = courseCodes.mapNotNull { mobilkoDB.coursesDao().getCoursesById(it) }

                adapter = CustomStudentRVAdapter(this, onItemClicked = { courses ->
                    val i = Intent(this, CourseDetailActivity::class.java)
                    val b = Bundle().apply {
                        putString("CourseName", courses.courseName)
                        putString("Course Code", courses.courseCode)
                        putString("Instructor", courses.instructorName)
                        putInt("stuId", stuID)
                    }
                    i.putExtras(b)
                    startActivity(i)
                })

                adapter.setData(takenCourses)
                bindingStu.courseTable.layoutManager = LinearLayoutManager(this)
                bindingStu.courseTable.adapter = adapter
            }
        })

        bindingStu.officeHourButton.setOnClickListener {
            val intent = Intent(this, OfficeHourActivity::class.java)
            intent.putExtra("Student_Id", stuID)
            startActivity(intent)
        }

        bindingStu.enrollBtn.setOnClickListener{
            createDialog(stuID)
        }

    }


    fun createDialog(stuId : Int) {
        customDialog = Dialog(this)
        customDialog.setContentView(R.layout.dialog_enroll)
        var btnOk : Button = customDialog.findViewById(R.id.button2)
        var btnCancel: Button = customDialog.findViewById(R.id.button3)
        var spinner: Spinner = customDialog.findViewById(R.id.spinner2)
        var selectedCourse = ""
        val courses = mobilkoDB.coursesDao().getCodes()
        val taken = mobilkoDB.coursesTakenDao().getCourseCodesById(stuId)

        val notTakenCourses = courses.filter { it !in taken }

        btnOk.setOnClickListener { Log.d("OK CLICKED" , "CLICKED") }
        Log.d("NOT TAKEN", notTakenCourses.toString())

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            notTakenCourses
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

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
        btnOk.setOnClickListener {
            var courseTaken = CoursesTaken(selectedCourse, stuId,0, 0)
            stuViewModel.addTakenCourse(courseTaken)
            Toast.makeText(this@StudentMainActivity, "$selectedCourse is added", Toast.LENGTH_SHORT).show()
            customDialog.dismiss()
        }
        btnCancel.setOnClickListener { customDialog.dismiss() }
        customDialog.show()
    }
}