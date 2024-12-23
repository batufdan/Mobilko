package com.zeynepturk.project_487.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "admin")
class Admin (@PrimaryKey(autoGenerate = true)
             var id: Int = 0,
             var pass: String,
             var name: String
) {
}