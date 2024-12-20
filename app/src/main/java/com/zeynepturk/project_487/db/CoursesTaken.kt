package com.zeynepturk.project_487.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coursesTaken")
class CoursesTaken (
    @PrimaryKey(autoGenerate = true)
    var stuID: Int = 0,
    @ColumnInfo(name = "CourseCode")
    var coursesTaken: String,
    @ColumnInfo(name = "Attendance")
    var attendance: String,
    @ColumnInfo(name = "CourseGrade")
    var courseGrade: String,
) {
}