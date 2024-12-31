package com.zeynepturk.project_487.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.zeynepturk.project_487.model.CoursesTaken
import com.zeynepturk.project_487.model.Instructor

@Dao
interface CoursesTakenDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTaken(coursesTaken: CoursesTaken): Long // suspend is written because it will be used with coroutine

    @Update
    fun updateTaken(coursesTaken: CoursesTaken): Int

    @Delete
    fun deleteTaken(coursesTaken: CoursesTaken): Int

    @Query("DELETE FROM CoursesTaken")
    fun deleteAllTakens()

    @Query("SELECT * FROM CoursesTaken WHERE stuID = :id ORDER BY coursesCode ASC")
    fun getAllTakenById(id: Int): LiveData<List<CoursesTaken>>

    @Query("SELECT * FROM coursesTaken WHERE coursesCode =:id")
    fun getTakenById(id: Int): CoursesTaken

    @Query("SELECT * FROM coursesTaken WHERE stuID = :studentId")
    fun getCoursesTakenByStudentId(studentId: Int): List<CoursesTaken>

    @Query("SELECT * from coursesTaken WHERE stuID = :id AND coursesCode = :code")
    fun getAttGradeByIdAndCode(id: Int, code: String) : CoursesTaken

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllTakens(coursesTaken: ArrayList<CoursesTaken>) {
        coursesTaken.forEach {
            insertTaken(it)
        }
    }
}