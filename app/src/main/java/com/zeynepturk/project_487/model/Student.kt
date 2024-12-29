package com.zeynepturk.project_487.model

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "student")
class Student(
    @PrimaryKey(autoGenerate = true) // True yapınca patladı?
    @SerializedName("id") var id: Int = 0,
    @SerializedName("pass") var pass: String,
    @SerializedName("name") var name: String,
    @SerializedName("mail") var mail: String,
    @SerializedName("cgpa") var cgpa: Double
){
    companion object {
        const val TYPE_SUCCESSFUL = 100
        const val TYPE_UNSUCCESSFUL = 200
    }

    fun calculateType():Int {
        return if(cgpa >=2)
            TYPE_SUCCESSFUL
        else
            TYPE_UNSUCCESSFUL
    }
    override fun toString(): String {
        return "Student(id=$id, name=$name, pass=$pass, mail=$mail, cgpa=$cgpa)"
    }
}