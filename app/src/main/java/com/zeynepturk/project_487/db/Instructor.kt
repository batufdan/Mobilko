package com.zeynepturk.project_487.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "instructor")
class Instructor(
    @PrimaryKey(autoGenerate = true)
    var instructorID: Int,
    var instructorName: String,
    var instructorOfficeHour: String,
    var isTaken: String,
    var instructorMail: String
) {
}