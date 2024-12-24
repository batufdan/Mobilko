package com.zeynepturk.project_487

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.zeynepturk.project_487.databinding.ActivityStuMainBinding
import com.zeynepturk.project_487.db.MobilkoRoomDatabase

class StudentMainActivity : AppCompatActivity() {
    lateinit var bindingStu: ActivityStuMainBinding
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


        bindingStu.officeHourButton.setOnClickListener {
            val intent = Intent(this, OfficeHourActivity::class.java)
            intent.putExtra("Student_Id", stuID)
            startActivity(intent)
        }

    }
}