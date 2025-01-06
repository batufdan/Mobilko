package com.zeynepturk.project_487

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import com.zeynepturk.project_487.databinding.ActivityCourseDetailBinding
import com.zeynepturk.project_487.db.MobilkoRoomDatabase

class CourseDetailActivity : AppCompatActivity() {
    lateinit var bindingDetail : ActivityCourseDetailBinding
    private val mobilkoDB: MobilkoRoomDatabase by lazy {
        Room.databaseBuilder(this, MobilkoRoomDatabase::class.java, "MobilkoDB")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        bindingDetail = ActivityCourseDetailBinding.inflate(layoutInflater)
        setContentView(bindingDetail.root)

        val i = intent.extras

        val courseCode = i?.getString("Course Code")
        val stuId = i?.getInt("stuId")
        val courseName = i?.getString("CourseName")
        val inst = i?.getString("Instructor")
        Log.d("CODE", courseCode.toString())
        Log.d("ID", stuId.toString())
        Log.d("NAME", courseName.toString())

        val takenCourse =
            stuId?.let { mobilkoDB.coursesTakenDao().getAttGradeByIdAndCode(it, courseCode.toString()) }

        bindingDetail.courseCode.text = courseCode
        bindingDetail.courseName.text = courseName
        bindingDetail.attendance.text = takenCourse?.attendance.toString()
        bindingDetail.grade.text = takenCourse?.courseGrade.toString()
        bindingDetail.instructor.text = inst
        bindingDetail.backBtn.setOnClickListener {
            finish()
        }

    }
}