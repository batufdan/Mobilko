package com.zeynepturk.project_487.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zeynepturk.project_487.model.Admin
import com.zeynepturk.project_487.model.Courses
import com.zeynepturk.project_487.model.CoursesTaken
import com.zeynepturk.project_487.model.Instructor
import com.zeynepturk.project_487.model.Student

@Database(entities = [Student::class, Admin::class, Instructor::class, Courses::class, CoursesTaken::class], version = 4)
abstract class MobilkoRoomDatabase : RoomDatabase() {
    abstract fun studentDao(): StudentDAO
    abstract fun adminDao(): AdminDAO
    abstract fun coursesDao(): CoursesDAO
    abstract fun coursesTakenDao(): CoursesTakenDAO
    abstract fun instructorDao(): InstructorDAO
}