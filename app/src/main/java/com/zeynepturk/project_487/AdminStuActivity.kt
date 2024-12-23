package com.zeynepturk.project_487

import android.os.Bundle
import android.text.TextWatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.zeynepturk.project_487.databinding.ActivityAdminStuBinding

class AdminStuActivity : AppCompatActivity() {
    lateinit var bindingAdminStu: ActivityAdminStuBinding
    private val stuDB:
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        bindingAdminStu = ActivityAdminStuBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_admin_stu)


        bindingAdminStu.search.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    performSearch(s.toString())
                }

                override fun afterTextChanged(s: Editable?) {
                    // Harf yazıldıktan sonra yapılacak işlemler
                }
        })
    }

    private fun searchStu(s : String){

    }
}