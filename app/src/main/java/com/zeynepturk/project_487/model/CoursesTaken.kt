package com.zeynepturk.project_487.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coursesTaken",
    primaryKeys = ["coursesCode", "stuID"])
class CoursesTaken (
    var coursesCode: String,
    var stuID: Int,
    var attendance: Int,
    var courseGrade: Int
) {
}