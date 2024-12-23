package com.zeynepturk.project_487.db

import android.app.Application
import androidx.annotation.RestrictTo
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.room.Room
import com.zeynepturk.project_487.model.Student
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/*
it provides data to the UI and survive configuration changes. It acts as a communication center between repository and the UI
 */
class AdminStuViewModel(application:Application):AndroidViewModel(application) {
    val readAllData: LiveData<MutableList<Student>>
    val studentDAO: StudentDAO
    private val mobilkoDB: MobilkoRoomDatabase by lazy {
        Room.databaseBuilder(application, MobilkoRoomDatabase::class.java, "MobilkoDB")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
    init {
        studentDAO= mobilkoDB.studentDao()
        readAllData = studentDAO.getAllStudents()
    }
    fun addStudent(student: Student){
        viewModelScope.launch(Dispatchers.IO){ // that code will be run in background thread, coroutine scope
            studentDAO.insertStudent(student)
        }
    }
    fun addStudent(book: MutableList<Student>){
        viewModelScope.launch(Dispatchers.IO) { // that code will be run in background thread, coroutine scope
            book.forEach{
                studentDAO.insertStudent(it)
            }
        }
    }

    fun updateCustomer(student: Student) {
        viewModelScope.launch(Dispatchers.IO) { // that code will be run in background thread, coroutine scope
           studentDAO.updateStudent(student)

        }
    }

    fun searchStudent(name: String) : MutableList<Student> {
        return studentDAO.getStudentsBySearchKey(name)
    }
}