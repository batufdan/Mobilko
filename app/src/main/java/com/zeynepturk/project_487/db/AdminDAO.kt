package com.zeynepturk.project_487.db

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.zeynepturk.project_487.model.Admin
import com.zeynepturk.project_487.model.Courses

interface AdminDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAdmin(admin: Admin): Long
}