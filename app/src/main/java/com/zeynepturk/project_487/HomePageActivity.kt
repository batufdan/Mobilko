package com.zeynepturk.project_487

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.ctis487.workerjsondatabase.backgroundservice.CustomWorker
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.zeynepturk.project_487.databinding.ActivityHomePageBinding
import com.zeynepturk.project_487.db.AdminCourseViewModel
import com.zeynepturk.project_487.db.AdminStuViewModel
import com.zeynepturk.project_487.db.MobilkoRoomDatabase
import com.zeynepturk.project_487.model.Admin
import com.zeynepturk.project_487.model.Courses
import com.zeynepturk.project_487.model.CoursesTaken
import com.zeynepturk.project_487.model.Instructor
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
    lateinit var stuList: ArrayList<Student>
    lateinit var admins:  ArrayList<Admin>
    lateinit var instructors: List<Instructor>
    lateinit var coursesTakenList: ArrayList<CoursesTaken>
    lateinit var studentService: StudentService
    lateinit var stuViewModel: AdminStuViewModel
    lateinit var initialCourses: List<Courses>
    lateinit var courseViewModel: AdminCourseViewModel
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
        stuList = ArrayList<Student>()

        // clearData()    // Test datalarını temizlemek için
        stuViewModel = ViewModelProvider(this).get(AdminStuViewModel::class.java)
        courseViewModel = ViewModelProvider(this).get(AdminCourseViewModel::class.java)
        getData()


        binding.adminBtn.setOnClickListener {
            val enteredId = binding.studentIdInput.text.toString().trim()
            val enteredPass = binding.passwordInput.text.toString().trim()

            if (enteredId.isEmpty() || enteredPass.isEmpty()) {
                showToast("Please fill in both fields!")
            }
            else{
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
        }

        binding.studentBtn.setOnClickListener {
            val enteredId = binding.studentIdInput.text.toString().trim()
            val enteredPass = binding.passwordInput.text.toString().trim()

            if (enteredId.isEmpty() || enteredPass.isEmpty()) {
                showToast("Please fill in both fields!")
            }else{
                Log.d("STULIST", stuList.toString())
                val foundStudent = stuList.find {
                    Log.d("FOUND STU", it.id.toString() + it.pass.toString())
                    it.id == enteredId.toInt() && it.pass == enteredPass
                }

                if (foundStudent != null) {
                    showToast("Welcome, ${foundStudent.name}!")
                    Log.d("ENTERED ID", enteredId)
                    val intent = Intent(this, StudentMainActivity::class.java).apply {
                        putExtra("STUDENT_ID", enteredId.toInt())
                    }
                    startActivity(intent)
                    finish()
                } else {
                    showToast("Invalid ID or Password!")
                }
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
                    students = (response.body() as ArrayList<Student>)!!
                }
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

        instructors = listOf(
            Instructor(1, "Neşe Özçelik", "Monday 14:30-15:20", "Not Taken", "nozcelik@bilkent.edu.tr", R.drawable.kiz1),
            Instructor(2, "Cüneyt Sevgi", "Monday 13:30-14:20", "Not Taken", "csevgi@bilkent.edu.tr", R.drawable.cuneyt_sevgi),
            Instructor(3, "Ahmet Muhtar Güloğlu", "Tuesday 11:30-12:20", "Not Taken", "guloglua@fen.bilkent.edu.tr", R.drawable.ahmet_muhtar),
            Instructor(4, "Seyid Amjad", "Thursday 11:30-12:20", "Not Taken", "amjadali@bilkent.edu.tr", R.drawable.erkek),
            Instructor(5, "Leyla Sezer", "Friday 11:30-12:20", "Taken", "sezerleyla@bilkent.edu.tr", R.drawable.kiz2),
            Instructor(6, "Özen Baş", "Friday 10:30-11:20", "Taken", "ozenbas@bilkent.edu.tr", R.drawable.kiz3),
            Instructor(7, "Kudret Emiroğlu", "Wednesday 11:30-12:20", "Not Taken", "kudretemiroglu@bilkent.edu.tr", R.drawable.kudret_emir)
        )

        initialCourses = listOf(
            Courses("CTIS-487", "Mobile Application Development", "Leyla Sezer"),
            Courses("CTIS-359", "Software Engineering Principles", "Cüneyt Sevgi"),
            Courses("MATH-220", "Linear Algebra", "Ahmet Muhtar Güloğlu"),
            Courses("COMD-358", "Professional Communication", "Özen Baş"),
            Courses("CTIS-365", "Applied Data Analysis", "Seyid Amjad"),
            Courses("HIST-200", "History of Turkey", "Kudret Emiroğlu")
        )
    }


    private fun getData(){
        prepareData()
        val gson = Gson()
        val adminsJson = gson.toJson(admins)
        val instructorJson = gson.toJson(instructors)
        val coursesTakenJson = gson.toJson(coursesTakenList)
        val coursesJson = gson.toJson(initialCourses)

        val workRequest = OneTimeWorkRequest.Builder(CustomWorker::class.java)
            .setInputData(
                Data.Builder()
                    .putString("admins", adminsJson)
                    .putString("instructors", instructorJson)
                    .putString("coursesTaken", coursesTakenJson)
                    .putString("courses", coursesJson)
                    .build()
            )
            .build()

        val workManager = WorkManager.getInstance(applicationContext)
        workManager.enqueue(workRequest)

        workManager.getWorkInfoByIdLiveData(workRequest.id).observe(this, Observer { workInfo ->
            if (workInfo != null && workInfo.state == WorkInfo.State.SUCCEEDED) {
                val resultData = workInfo.outputData
                Snackbar.make(findViewById(android.R.id.content), "SUCCEEDED: ${resultData.getString("result")}", Snackbar.LENGTH_LONG).show()
            }
        })

        stuViewModel.readAllData.observe(this) { students ->
            stuList.addAll(students)
        }

    }

    private fun clearData() {
        // Student Table
        mobilkoDB.studentDao().deleteAllStudents()
        mobilkoDB.studentDao().resetAutoIncrement()
        mobilkoDB.instructorDao().deleteAllInstructor()
        mobilkoDB.coursesDao().deleteAllCourses()
        // CoursesTaken Table
        mobilkoDB.coursesTakenDao().deleteAllTakens()
    }
}
