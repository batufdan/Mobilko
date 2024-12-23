package com.zeynepturk.project_487.model

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@SuppressLint("ParcelCreator")
@Entity(tableName = "student")

class Student(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var pass: String,
    var name: String,
    var mail: String,
    var cgpa: Double
) : Parcelable {
    companion object CREATOR : Parcelable.Creator<Student> {
        override fun createFromParcel(parcel: Parcel): Student {
            return Student(parcel)
        }

        override fun newArray(size: Int): Array<Student?> {
            return arrayOfNulls(size)
        }
        const val TYPE_SUCCESSFUL = 100
        const val TYPE_UNSUCCESSFUL = 200
    }

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readDouble()
    ) {
    }

    fun calculateType():Int {
        return if(cgpa >=2)
            TYPE_SUCCESSFUL
        else
            TYPE_UNSUCCESSFUL
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(pass)
        parcel.writeString(name)
        parcel.writeString(mail)
        parcel.writeDouble(cgpa)
    }

    override fun describeContents(): Int {
        return 0
    }


}