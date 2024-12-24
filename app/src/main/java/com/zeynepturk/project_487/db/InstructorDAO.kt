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

    @Query("DELETE FROM instructor")
    fun deleteAllInstructor()


    @Query("SELECT * FROM instructor ORDER BY instructorID ASC")
    fun getAllInstructors(): LiveData<List<Instructor>>

    @Query("SELECT * FROM instructor WHERE instructorName = :name")
    fun getInstructorByName(name: String): Instructor?

    @Query("UPDATE instructor SET isTaken = 'Taken' WHERE instructorName = :name")
    fun toggleIsTakenByName(name: String): Int


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllInstructors(instructors: List<Instructor>) {
        instructors.forEach {
            insertInstructor(it)
        }
    }



}