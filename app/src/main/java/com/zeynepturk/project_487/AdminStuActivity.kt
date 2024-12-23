package com.zeynepturk.project_487

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.zeynepturk.project_487.adapter.CustomAdminStuRecyclerViewAdapter
import com.zeynepturk.project_487.databinding.ActivityAdminStuBinding
import com.zeynepturk.project_487.db.MobilkoRoomDatabase
import com.zeynepturk.project_487.model.Student
import kotlinx.coroutines.flow.Flow

class AdminStuActivity : AppCompatActivity() {
    lateinit var bindingAdminStu: ActivityAdminStuBinding
    lateinit var searchList : Flow<List<Student>>
    lateinit var adapter: CustomAdminStuRecyclerViewAdapter
    private val mobilkoDB: MobilkoRoomDatabase by lazy {
        Room.databaseBuilder(this, MobilkoRoomDatabase::class.java, "MobilkoDB")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        bindingAdminStu = ActivityAdminStuBinding.inflate(layoutInflater)
        setContentView(bindingAdminStu.root)

        adapter = CustomAdminStuRecyclerViewAdapter(this)
        bindingAdminStu.stuList.setLayoutManager(LinearLayoutManager(this))
        bindingAdminStu.stuList.adapter = adapter
        bindingAdminStu.search.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    searchStu(s.toString())
                }

                override fun afterTextChanged(s: Editable?) {
                    searchStu(s.toString())
                }
        })
    }

    private fun searchStu(s : String){
        searchList = mobilkoDB.studentDao().getStudentsBySearchKey(s)
    }
}