package com.zeynepturk.project_487.db

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class StudentMainViewModelFactory(
    private val application: Application,
    private val stuId: Int
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StudentMainViewModel::class.java)) {
            return StudentMainViewModel(application, stuId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}