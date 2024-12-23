package com.zeynepturk.project_487

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.zeynepturk.project_487.databinding.ActivityAdminMainBinding

class AdminMainActivity : AppCompatActivity() {
    lateinit var bindingAdmin: ActivityAdminMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        bindingAdmin = ActivityAdminMainBinding.inflate(layoutInflater)
        setContentView(bindingAdmin.root)

        bindingAdmin.btnStu.setOnClickListener {
            startActivity(Intent(this, AdminStuActivity::class.java))
        }

        bindingAdmin.btnCourse.setOnClickListener {
            startActivity(Intent(this, AdminCourseActivity::class.java))
        }
    }
}