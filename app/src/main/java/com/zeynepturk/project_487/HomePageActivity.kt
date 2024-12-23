package com.zeynepturk.project_487

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.zeynepturk.project_487.databinding.ActivityHomePageBinding
import com.zeynepturk.project_487.db.MobilkoRoomDatabase
import com.zeynepturk.project_487.model.Admin
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
                val intent = Intent(this, AdminMainActivity::class.java).apply {
                    putExtra("ADMIN_ID", enteredId)
                }
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

        val request = studentService.getStudents()
        request.enqueue(object : Callback<ArrayList<Student>> {
            override fun onFailure(call: Call<ArrayList<Student>>, t: Throwable) {
                Toast.makeText(applicationContext, t.message.toString(), Toast.LENGTH_LONG).show()
            }
            override fun onResponse(call: Call<ArrayList<Student>>, response: Response<ArrayList<Student>>) {
                if (response.isSuccessful) {
                    students = (response.body() as ArrayList<Student>?)!!
                }
            }
        })

        Collections.addAll(
            admins,
            Admin(1, "duruko16", "Duru Kırcı"),
            Admin(2, "zeyno1", "Zeynep Türk"),
            Admin(3, "1111", "Batuhan Fidan"),
            Admin(4, "sueda55", "Sueda Akça")
        )
    }


    private fun getData(){
        prepareData()
        mobilkoDB.studentDao().insertAllStudent(students)
        mobilkoDB.adminDao().insertAllAdmin(admins)
    }
}
