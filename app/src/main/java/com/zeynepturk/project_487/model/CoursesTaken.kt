package com.zeynepturk.project_487.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coursesTaken")
class CoursesTaken (
    @PrimaryKey(autoGenerate = false)
    var coursesCode: String,
    @PrimaryKey(autoGenerate = false)
    var stuID: Int,
    var attendance: Int,
    var courseGrade: Int
) {
}