package com.zeynepturk.project_487

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.map
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.zeynepturk.project_487.adapter.CustomInstructorRecyclerViewAdapter
import com.zeynepturk.project_487.databinding.ActivityOfficeHourBinding
import com.zeynepturk.project_487.db.MobilkoRoomDatabase
import com.zeynepturk.project_487.model.Courses
import com.zeynepturk.project_487.model.CoursesTaken
import com.zeynepturk.project_487.model.Instructor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OfficeHourActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOfficeHourBinding
    private val instructorList = mutableListOf<Instructor>()
    lateinit var taken: LiveData<MutableList<CoursesTaken>>
    private lateinit var adapter: CustomInstructorRecyclerViewAdapter
    private val mobilkoDB: MobilkoRoomDatabase by lazy {
        Room.databaseBuilder(this, MobilkoRoomDatabase::class.java, "MobilkoDB")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOfficeHourBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val i = intent.extras
        val stuID = i!!.getInt("Student_Id")

        if (stuID == -1) {
            Toast.makeText(this, "Invalid Student ID", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        taken = mobilkoDB.coursesTakenDao().getAllTakenById(stuID)

        taken.observe(this, Observer { coursesTakenList ->
            if (coursesTakenList != null) {
                val courseCodes: ArrayList<String> =
                    ArrayList(coursesTakenList.map { it.coursesCode })

                val instName : ArrayList<String> = ArrayList(courseCodes.map {
                    mobilkoDB.coursesDao().getInstNameByCode(it)
                })

                val foundInstructors = ArrayList<Instructor>()
                instName.forEach { name ->
                    val instructor = mobilkoDB.instructorDao().getInstructorByName(name)
                    if (instructor == null) {
                        Log.d("MISSING_INSTRUCTOR", "No match for name: $name")
                    } else {
                        foundInstructors.add(instructor)
                    }
                }
                adapter = CustomInstructorRecyclerViewAdapter(this, foundInstructors, mobilkoDB.instructorDao()) { instructor ->
                    handleTakeClick(instructor) }
                binding.instructorRecycler.layoutManager = LinearLayoutManager(this)
                binding.instructorRecycler.adapter = adapter
                }
        })
    }

    private fun handleTakeClick(instructor: Instructor) {
        if (instructor.isTaken == "Not Taken") {
            Toast.makeText(this, "${instructor.instructorName}'s office hour taken!", Toast.LENGTH_SHORT).show()

            mobilkoDB.instructorDao().toggleIsTakenByName(instructor.instructorName)
            // Update the list locally
            val index = instructorList.indexOf(instructor)
            if (index != -1) {
                instructorList[index].isTaken = "Taken"
                adapter.notifyItemChanged(index)
            }
        } else {
            Toast.makeText(this, "This office hour is already taken.", Toast.LENGTH_SHORT).show()
        }
    }

}