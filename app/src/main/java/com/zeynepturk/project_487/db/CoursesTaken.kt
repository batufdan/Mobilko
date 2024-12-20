package com.zeynepturk.project_487.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coursesTaken")
class CoursesTaken (
    @PrimaryKey(autoGenerate = true)
    var stuID: Int = 0,
    var coursesCode: String,
    var attendance: Int,
    var courseGrade: Int
) {
}