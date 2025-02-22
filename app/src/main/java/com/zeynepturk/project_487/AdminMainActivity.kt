package com.zeynepturk.project_487

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.zeynepturk.project_487.databinding.ActivityAdminMainBinding
import com.zeynepturk.project_487.model.Student


class AdminMainActivity : AppCompatActivity() {
    lateinit var bindingAdmin: ActivityAdminMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        bindingAdmin = ActivityAdminMainBinding.inflate(layoutInflater)
        setContentView(bindingAdmin.root)

        bindingAdmin.btnStu.setOnClickListener {
            val intent = Intent(this, AdminStuActivity::class.java)
            startActivity(intent)
        }

        bindingAdmin.btnCourse.setOnClickListener {
            startActivity(Intent(this, AdminCourseActivity::class.java))
        }

        bindingAdmin.btnLogout.setOnClickListener {
            startActivity(Intent(this, HomePageActivity::class.java))
        }

        Glide.with(this)
            .load(R.drawable.blue_graduation_cap_icon)
            .circleCrop()
            .into(bindingAdmin.logoImage);
    }
}