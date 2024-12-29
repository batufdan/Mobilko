package com.zeynepturk.project_487.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.zeynepturk.project_487.model.Student
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStudent(student: Student): Long // suspend is written because it will be used with coroutine

    @Update
    fun updateStudent(student: Student): Int

    @Delete
    fun deleteStudent(student: Student): Int

    @Query("DELETE FROM student")
    fun deleteAllStudents()

    @Query("SELECT * FROM student ORDER BY id ASC")
    fun getAllStudents(): LiveData<MutableList<Student>>

    @Query("SELECT * FROM student WHERE id =:id")
    fun getStudentById(id: Int): Student

    @Query("SELECT * FROM student WHERE name LIKE :searchKey")
    fun getStudentsBySearchKey(searchKey: String): MutableList<Student>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllStudent(students: ArrayList<Student>) {
        students.forEach {
            insertStudent(it)
        }
    }

    @Query("DELETE FROM sqlite_sequence WHERE name = 'student'")
    fun resetAutoIncrement()
}