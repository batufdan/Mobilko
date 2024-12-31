package com.zeynepturk.project_487.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "instructor")
class Instructor(
    @PrimaryKey(autoGenerate = true)
    var instructorID: Int = 0,
    var instructorName: String,
    var instructorOfficeHour: String,
    var isTaken: String,
    var instructorMail: String,
    var img: Int
) {
    override fun toString(): String {
        return "Name = $instructorName, officeHour = $instructorOfficeHour"
    }
}