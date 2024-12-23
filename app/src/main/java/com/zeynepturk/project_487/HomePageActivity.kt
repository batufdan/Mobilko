package com.zeynepturk.project_487

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.zeynepturk.project_487.databinding.ActivityHomePageBinding
import com.zeynepturk.project_487.db.MobilkoRoomDatabase
import com.zeynepturk.project_487.model.Admin
import com.zeynepturk.project_487.model.CoursesTaken
import com.zeynepturk.project_487.model.Student
import com.zeynepturk.project_487.retrofit.StudentService
import com.zeynepturk.project_487.util.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Collections

class HomePageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomePageBinding
    lateinit var students: ArrayList<Student>
    lateinit var admins:  ArrayList<Admin>
    lateinit var coursesTakenList: ArrayList<CoursesTaken>
    lateinit var studentService: StudentService
    private val mobilkoDB: MobilkoRoomDatabase by lazy {
        Room.databaseBuilder(this, MobilkoRoomDatabase::class.java, "MobilkoDB")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        studentService = ApiClient.getClient()
            .create(StudentService::class.java)

        students = ArrayList<Student>()
        getData()

        binding.adminBtn.setOnClickListener {
            val enteredId = binding.studentIdInput.text.toString().trim()
            val enteredPass = binding.passwordInput.text.toString().trim()

            if (enteredId.isEmpty() || enteredPass.isEmpty()) {
                showToast("Please fill in both fields!")
            }
            val foundAdmin = admins.find {
                it.id == enteredId.toInt() && it.pass == enteredPass
            }

            if (foundAdmin != null) {
                showToast("Welcome, ${foundAdmin.name}!")
                val intent = Intent(this, AdminMainActivity::class.java)
                intent.putExtra("adminId", it.id)
                startActivity(intent)
                finish()
            } else {
                showToast("Invalid ID or Password!")
            }
        }

        binding.studentBtn.setOnClickListener {
            val enteredId = binding.studentIdInput.text.toString().trim()
            val enteredPass = binding.passwordInput.text.toString().trim()

            if (enteredId.isEmpty() || enteredPass.isEmpty()) {
                showToast("Please fill in both fields!")
            }

            val foundStudent = students.find {
                it.id == enteredId.toInt() && it.pass == enteredPass
            }

            if (foundStudent != null) {
                showToast("Welcome, ${foundStudent.name}!")
                val intent = Intent(this, StudentMainActivity::class.java).apply {
                    putExtra("STUDENT_ID", enteredId)
                }
                startActivity(intent)
                finish()
            } else {
                showToast("Invalid ID or Password!")
            }
        }

    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun prepareData() {
        admins = ArrayList<Admin>()
        coursesTakenList = ArrayList<CoursesTaken>()
        val request = studentService.getStudents()
        request.enqueue(object : Callback<List<Student>> {
            override fun onFailure(call: Call<List<Student>>, t: Throwable) {
                Log.d("FAIL JSON", t.message.toString())
            }
            override fun onResponse(call: Call<List<Student>>, response: Response<List<Student>>) {
                if (response.isSuccessful) {
                    Log.d("RESPONSE SUCCESSFUL", response.body().toString())
                    students = (response.body() as ArrayList<Student>?)!!
                    Log.d("RESPONSE SUCCESSFUL", students.toString())
                }
                Log.d("RESPONSE BLA BLA", "ne yaptığını bilmiyoz")
                mobilkoDB.studentDao().insertAllStudent(students)
            }
        })

        Collections.addAll(
            admins,
            Admin(1, "duruko16", "Duru Kırcı"),
            Admin(2, "zeyno1", "Zeynep Türk"),
            Admin(3, "1111", "Batuhan Fidan"),
            Admin(4, "sueda55", "Sueda Akça")
        )


        Collections.addAll(
            coursesTakenList,
            CoursesTaken("CTIS-487", 1, 80, 100),
            CoursesTaken("CTIS-365", 1, 50, 70),
            CoursesTaken("CTIS-359", 1, 90, 30),
            CoursesTaken("CTIS-487", 2, 40, 75),
            CoursesTaken("CTIS-365", 2, 10, 56),
            CoursesTaken("COMD-358", 3, 100, 68),
            CoursesTaken("HIST-200", 3, 80, 100),
            CoursesTaken("CTIS-487", 4, 100, 70),
            CoursesTaken("CTIS-359", 4, 90, 80)
        )
    }


    private fun getData(){
        prepareData()
        mobilkoDB.adminDao().insertAllAdmin(admins)
        mobilkoDB.coursesTakenDao().insertAllTakens(coursesTakenList)
    }
}
