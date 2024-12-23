package com.zeynepturk.project_487.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.zeynepturk.project_487.model.Student

@Dao
interface StudentDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStudent(student: Student): Long // suspend is written because it will be used with coroutine

    @Update
    fun updateStudent(student: Student): Int

    @Delete
    fun deleteStudent(student: Student): Int

    @Query("SELECT * FROM Student ORDER BY id ASC")
    fun getAllStudents(): LiveData<List<Student>>

    @Query("SELECT * FROM Student WHERE id =:id")
    fun getStudentById(id: Int): Student

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllStudent(students: ArrayList<Student>) {
        students.forEach {
            insertStudent(it)
        }
    }

}