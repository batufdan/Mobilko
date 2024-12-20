package com.zeynepturk.project_487.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "courses")
class Courses(
    @PrimaryKey(autoGenerate = true)
    var instructorID: Int,
    @ColumnInfo(name = "CourseCode")
    var courseCode: String,
    @ColumnInfo(name = "CourseName")
    var courseName: String,
    @ColumnInfo(name = "CourseExamDate")
    var crsExamDate: String,
) {
}