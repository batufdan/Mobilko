package com.zeynepturk.project_487.db

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.zeynepturk.project_487.model.CoursesTaken

interface CoursesTakenDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTaken(coursesTaken: CoursesTaken): Long // suspend is written because it will be used with coroutine

    @Update
    fun updateTaken(coursesTaken: CoursesTaken): Int

    @Delete
    fun deleteTaken(coursesTaken: CoursesTaken): Int

    @Query("DELETE FROM CoursesTaken")
    fun deleteAllTakens()

    /*
    LiveData is an observable data holder class. Unlike a regular observable, LiveData is lifecycle-aware,
    meaning it respects the lifecycle of other app components, such as activities, fragments, or services.
    This awareness ensures LiveData only updates app component observers that are in an active lifecycle state
    */

    @Query("SELECT * FROM CoursesTaken ORDER BY coursesCode ASC")
    fun getAllTakens(): LiveData<List<CoursesTaken>>
    // LiveData means aware of the modification

    @Query("SELECT * FROM CoursesTaken WHERE coursesCode =:id")
    fun getTakenById(id: Int): CoursesTaken

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllTakens(customers: ArrayList<CoursesTaken>) {
        customers.forEach {
            insertTaken(it)
        }
    }

}