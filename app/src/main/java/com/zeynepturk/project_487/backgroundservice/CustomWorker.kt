package com.ctis487.workerjsondatabase.backgroundservice

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.work.Data
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.zeynepturk.project_487.db.MobilkoRoomDatabase
import com.zeynepturk.project_487.model.Admin


class CustomWorker(var context: Context, var workerParams: WorkerParameters, ):Worker (context, workerParams){
    private val mobilkoDB: MobilkoRoomDatabase by lazy {
        Room.databaseBuilder(context, MobilkoRoomDatabase::class.java, "MobilkoDB")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
    override fun doWork(): ListenableWorker.Result {
        /* context = applicationContext */
        val adminsJson = inputData.getString("admins")
        val adminDao = mobilkoDB.adminDao()

        val gson = Gson()
        val admins: ArrayList<Admin> = gson.fromJson(
            adminsJson,
            object : TypeToken<ArrayList<Admin>>() {}.type
        )
        return try {
            Log.d("CustomWorker", "Received Admins: $admins")
            adminDao.insertAllAdmin(admins)
            val outputData = Data.Builder()
                .putString("result", "Processed ${admins.size} admins")
                .build()
            ListenableWorker.Result.success(outputData)

        } catch (throwable: Throwable) {
            Log.d("WORKERJSONDATABASE", "Error Sending Notification" + throwable.message)
            ListenableWorker.Result.failure() //this will return
        }
    }
}