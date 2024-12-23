package com.zeynepturk.project_487.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.zeynepturk.project_487.model.Instructor
@Dao
interface InstructorDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInstructor(instructor: Instructor): Long // suspend is written because it will be used with coroutine

    @Update
    fun updateInstructor(instructor: Instructor): Int

    @Delete
    fun deleteInstructor(instructor: Instructor): Int

    @Query("DELETE FROM Instructor")
    fun deleteAllInstructor()


    @Query("SELECT * FROM Instructor ORDER BY instructorID ASC")
    fun getAllInstructors(): LiveData<List<Instructor>>
    // LiveData means aware of the modification

    @Query("SELECT * FROM Instructor WHERE instructorID =:id")
    fun getInstructorById(id: Int): Instructor

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllInstructors(customers: ArrayList<Instructor>) {
        customers.forEach {
            insertInstructor(it)
        }
    }

}