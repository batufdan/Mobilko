package com.zeynepturk.project_487.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.zeynepturk.project_487.model.Courses
import kotlinx.coroutines.flow.Flow
@Dao
interface CoursesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCourse(courses: Courses): Long // suspend is written because it will be used with coroutine

    @Update
    fun updateCourse(courses: Courses): Int

    @Delete
    fun deleteCourse(courses: Courses): Int

    @Query("DELETE FROM Courses")
    fun deleteAllCourses()

    @Query("SELECT * FROM Courses ORDER BY courseCode ASC")
    fun getAllCourses(): LiveData<List<Courses>>
    // LiveData means aware of the modification

    @Query("SELECT * FROM Courses WHERE courseCode =:id")
    fun getCoursesById(id: String): Courses

    @Query("SELECT * FROM Courses WHERE courseName LIKE :searchKey OR courseCode LIKE :searchKey")
    fun getCourseBySearchKey(searchKey: String): Flow<List<Courses>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllCourses(customers: ArrayList<Courses>) {
        customers.forEach {
            insertCourse(it)
        }
    }

}