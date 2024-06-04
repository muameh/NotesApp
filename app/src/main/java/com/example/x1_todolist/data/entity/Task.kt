package com.example.x1_todolist.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity(tableName = "task_table")
@Parcelize
data class Task(@PrimaryKey(autoGenerate = true) val task_id:Int,
                val task_title:String,
                val task_description:String,
                val task_deadline:String,
                val task_creation_time:String)
    :Parcelable
