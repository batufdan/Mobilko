package com.ctis487.workerjsondatabase.backgroundservice

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.work.Data
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.zeynepturk.project_487.db.MobilkoRoomDatabase
import com.zeynepturk.project_487.model.Admin
import com.zeynepturk.project_487.model.Courses
import com.zeynepturk.project_487.model.CoursesTaken
import com.zeynepturk.project_487.model.Instructor


class CustomWorker(var context: Context, var workerParams: WorkerParameters, ):Worker (context, workerParams){
    private val mobilkoDB: MobilkoRoomDatabase by lazy {
        Room.databaseBuilder(context, MobilkoRoomDatabase::class.java, "MobilkoDB")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
    override fun doWork(): ListenableWorker.Result {
        /* context = applicationContext */
        val adminsJson = inputData.getString("admins")
        val instJson = inputData.getString("instructors")
        val coursesTakenJson = inputData.getString("coursesTaken")
        val courses = inputData.getString("courses")

        val gson = Gson()
        val admins: ArrayList<Admin> = gson.fromJson(
            adminsJson,
            object : TypeToken<ArrayList<Admin>>() {}.type
        )
        Log.d("INSTRUCTION", instJson.toString())
        val instructions: List<Instructor> = gson.fromJson(
            instJson,
            object : TypeToken<List<Instructor>>() {}.type
        )
        val cTaken: ArrayList<CoursesTaken> = gson.fromJson(
            coursesTakenJson,
            object : TypeToken<ArrayList<CoursesTaken>>() {}.type
        )
        val courseList: ArrayList<Courses> = gson.fromJson(
            courses,
            object : TypeToken<ArrayList<Courses>>() {}.type
        )

        return try {
            Log.d("CustomWorker", "Received Admins: $admins")
            mobilkoDB.adminDao().insertAllAdmin(admins)
            mobilkoDB.coursesTakenDao().insertAllTakens(cTaken)
            mobilkoDB.instructorDao().insertAllInstructors(instructions)
            mobilkoDB.coursesDao().insertAllCourses(courseList)
            val outputData = Data.Builder()
                .putString("result", "Processed ${admins.size} admins, ${instructions.size} instruction, ${courseList.size} courses, ${cTaken.size} taken course")
                .build()
            ListenableWorker.Result.success(outputData)

        } catch (throwable: Throwable) {
            Log.d("WORKERJSONDATABASE", "Error Sending Notification" + throwable.message)
            ListenableWorker.Result.failure() //this will return
        }
    }
}