package com.example.x1_todolist.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.x1_todolist.data.entity.Task


@Dao
interface TaskDao {

    @Query("SELECT * FROM task_table")
    fun getAllTasks() : LiveData<List<Task>>

    @Insert
    suspend fun addTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)
}