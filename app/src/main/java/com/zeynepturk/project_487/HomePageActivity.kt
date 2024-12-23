package com.zeynepturk.project_487

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.zeynepturk.project_487.databinding.ActivityHomePageBinding
import com.zeynepturk.project_487.model.Admin
import com.zeynepturk.project_487.model.Student

class HomePageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomePageBinding
    private val admins = listOf(
        Admin(1, "duruko16", "Duru Kırcı"),
        Admin(2, "zeyno1", "Zeynep Türk"),
        Admin(3, "1111", "Batuhan Fidan"),
        Admin(4, "sueda55", "Sueda Akça")
    )

    private val students = listOf(
        Student(1, "duruko", "Duru Kırcı", "kirciduru@gmail.com"),
        Student(2, "zeyno", "Zeynep Türk", "zeynepturk74@gmail.com"),
        Student(3, "1111", "Batuhan Fidan", "batuhanfidan59@gmail.com"),
        Student(4, "sueda", "Sueda Akça","akcasueda@gmail.com")
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.adminBtn.setOnClickListener {
            val enteredId = binding.studentIdInput.text.toString().trim()
            val enteredPass = binding.passwordInput.text.toString().trim()

            if (enteredId.isEmpty() || enteredPass.isEmpty()) {
                showToast("Please fill in both fields!")
            }
            val foundAdmin = admins.find {
                it.id == enteredId.toInt() && it.pass == enteredPass
            }

            if (foundAdmin != null) {
                showToast("Welcome, ${foundAdmin.name}!")
                val intent = Intent(this, AdminMainActivity::class.java).apply {
                    putExtra("ADMIN_ID", enteredId)
                }
                startActivity(intent)
                finish()
            } else {
                showToast("Invalid ID or Password!")
            }
        }

        binding.studentBtn.setOnClickListener {
            val enteredId = binding.studentIdInput.text.toString().trim()
            val enteredPass = binding.passwordInput.text.toString().trim()

            if (enteredId.isEmpty() || enteredPass.isEmpty()) {
                showToast("Please fill in both fields!")
            }

            val foundStudent = students.find {
                it.id == enteredId.toInt() && it.pass == enteredPass
            }

            if (foundStudent != null) {
                showToast("Welcome, ${foundStudent.name}!")
                val intent = Intent(this, MainActivity::class.java).apply {
                    putExtra("STUDENT_ID", enteredId)
                }
                startActivity(intent)
                finish()
            } else {
                showToast("Invalid ID or Password!")
            }
        }

    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}