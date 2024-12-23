package com.zeynepturk.project_487.db

import androidx.room.Database
import com.zeynepturk.project_487.model.Admin
import com.zeynepturk.project_487.model.Courses
import com.zeynepturk.project_487.model.CoursesTaken
import com.zeynepturk.project_487.model.Instructor
import com.zeynepturk.project_487.model.Student

@Database(entities = [Student::class, Admin::class, Instructor::class, Courses::class, CoursesTaken::class], version = 1)
abstract class MobilkoRoomDatabase {
    abstract fun studentDao(): StudentDAO
    abstract fun adminDao(): AdminDAO
    abstract fun coursesDao(): CoursesDAO
    abstract fun coursesTakenDao(): CoursesTakenDAO
    abstract fun instructorDao(): InstructorDAO
}