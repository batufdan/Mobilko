package com.zeynepturk.project_487.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student")
class Student(
    @PrimaryKey(autoGenerate = true)
               var id: Int = 0,
               @ColumnInfo(name = "StudentName")
               var name: String,
               @ColumnInfo(name = "StudentPassword")
               var pass: String,
               @ColumnInfo(name = "StudentMail")
               var mail: String,
){

}