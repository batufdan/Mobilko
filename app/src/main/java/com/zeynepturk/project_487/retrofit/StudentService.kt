package com.zeynepturk.project_487.retrofit


import com.zeynepturk.project_487.model.Student
import retrofit2.Call
import retrofit2.http.GET

interface StudentService {
    @GET("https://jsonkeeper.com/b/JC0I/")
    fun getStudents(): Call<List<Student>>
}