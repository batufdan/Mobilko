package com.zeynepturk.project_487.db

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.zeynepturk.project_487.model.CoursesTaken
import com.zeynepturk.project_487.model.Student
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StudentMainViewModel(application: Application) : AndroidViewModel(application) {
    val readAllData: LiveData<MutableList<Student>>
    val studentDAO: StudentDAO
    val coursesTakenDAO: CoursesTakenDAO
    private val mobilkoDB: MobilkoRoomDatabase by lazy {
        Room.databaseBuilder(application, MobilkoRoomDatabase::class.java, "MobilkoDB")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
    init {
        coursesTakenDAO = mobilkoDB.coursesTakenDao()
        studentDAO= mobilkoDB.studentDao()
        readAllData = studentDAO.getAllStudents()
    }

    fun addTakenCourse(takenCourse: CoursesTaken){
        viewModelScope.launch(Dispatchers.IO){ // that code will be run in background thread, coroutine scope
            coursesTakenDAO.insertTaken(takenCourse)
        }
    }
}