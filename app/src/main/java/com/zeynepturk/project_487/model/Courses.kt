package com.zeynepturk.project_487.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "courses")
class Courses(
    @PrimaryKey(autoGenerate = true)
    var courseCode: String,
    var courseName: String,
    var instructorName: String
) {
}