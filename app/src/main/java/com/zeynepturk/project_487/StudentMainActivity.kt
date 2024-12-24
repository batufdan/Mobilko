package com.zeynepturk.project_487

import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.map
import androidx.room.Room
import com.zeynepturk.project_487.databinding.ActivityStuMainBinding
import com.zeynepturk.project_487.db.MobilkoRoomDatabase
import com.zeynepturk.project_487.model.Courses
import com.zeynepturk.project_487.model.CoursesTaken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StudentMainActivity : AppCompatActivity() {
    lateinit var bindingStu: ActivityStuMainBinding
    lateinit var taken: LiveData<List<CoursesTaken>>
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


        taken = mobilkoDB.coursesTakenDao().getAllTakenById(stuID)

        taken.observe(this, Observer { coursesTakenList ->
            if (coursesTakenList != null) {
                val courseCodes: ArrayList<String> =
                    ArrayList(coursesTakenList.map { it.coursesCode })

                val takenCourses : List<Courses> = courseCodes.map { mobilkoDB.coursesDao().getCoursesById(it) }
                bindingStu.temp.text = takenCourses.joinToString(","){it.courseName}

            }
        })

        bindingStu.officeHourButton.setOnClickListener {
            val intent = Intent(this, OfficeHourActivity::class.java)
            intent.putExtra("Student_Id", stuID)
            startActivity(intent)
        }

    }
}