package com.zeynepturk.project_487.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student")
class Student(
    @PrimaryKey(autoGenerate = true)
               var id: Int = 0,
               var pass: String,
               var name: String,
               var mail: String
){

}