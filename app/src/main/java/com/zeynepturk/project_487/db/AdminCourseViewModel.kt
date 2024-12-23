package com.zeynepturk.project_487.db

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.zeynepturk.project_487.model.Courses
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AdminCourseViewModel(application: Application): AndroidViewModel(application) {
    val readAllData: LiveData<List<Courses>>
    lateinit var courseDAO: CoursesDAO

    private val mobilkoDB: MobilkoRoomDatabase by lazy {
        Room.databaseBuilder(application, MobilkoRoomDatabase::class.java, "MobilkoDB")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    init {
        courseDAO = mobilkoDB.coursesDao()
        readAllData = courseDAO.getAllCourses()
    }

    fun addCourse(course: Courses) {
        viewModelScope.launch(Dispatchers.IO) {
            courseDAO.insertCourse(course)
        }
    }

    fun addCourses(courses: List<Courses>) {
        viewModelScope.launch(Dispatchers.IO) {
            courses.forEach {
                courseDAO.insertCourse(it)
            }
        }
    }

    fun deleteCourse(course: Courses, onResult: (Int) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val numberOfDeletedRecords = courseDAO.deleteCourse(course)
            withContext(Dispatchers.Main) {
                onResult(numberOfDeletedRecords)
            }
        }
    }


    fun updateCourse(course: Courses, onResult: (Int) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val numberOfUpdatedRecords = courseDAO.updateCourse(course)
            withContext(Dispatchers.Main) {
                onResult(numberOfUpdatedRecords)
            }
        }
    }

    fun searchCourse(searchkey: String): LiveData<List<Courses>> {
        return courseDAO.getCourseBySearchKey(searchkey).asLiveData()
    }
}