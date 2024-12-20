package com.zeynepturk.project_487

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.zeynepturk.project_487.databinding.ActivityHomePageBinding
import com.zeynepturk.project_487.model.Student

class HomePageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomePageBinding
    private val students = listOf(
        Student(1, "duruko16", "Duru Kırcı", "kirciduru@gmail.com"),
        Student(2, "zeyno1", "Zeynep Türk", "zeynepturk74@gmail.com"),
        Student(3, "1111", "Batuhan Fidan", "batuhanfidan59@hotmail.com"),
        Student(4, "sueda55", "Sueda Akça", "akcasueda@gmail.com")
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.loginButton.setOnClickListener {
            handleLogin()
        }

    }

    private fun handleLogin(){
        val enteredId = binding.studentIdInput.text.toString().trim()
        val enteredPass = binding.passwordInput.text.toString().trim()

        if (enteredId.isEmpty() || enteredPass.isEmpty()) {
            showToast("Please fill in both fields!")
            return
        }

        val foundStudent = students.find {
            it.id == enteredId.toInt() && it.password == enteredPass
        }

        if (foundStudent != null) {
            showToast("Welcome, ${foundStudent.name}!")
            navigateToMain(foundStudent.id)
        } else {
            showToast("Invalid Student ID or Password!")
        }

    }

    private fun navigateToMain(studentId: Int) {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("STUDENT_ID", studentId)
        }
        startActivity(intent)
        finish()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }


}