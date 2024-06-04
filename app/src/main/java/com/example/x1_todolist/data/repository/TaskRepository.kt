package com.example.x1_todolist.data.repository

import com.example.x1_todolist.data.datasource.DataSource
import com.example.x1_todolist.data.entity.Task

class TaskRepository (var datasource: DataSource){

    fun getAllTasks()  = datasource.getAllTasks()

    suspend fun addTask(task_title:String,
                        task_desc:String,
                        task_deadLine:String,
                        task_creation_time:Pair<String, String>) {
        datasource.addTask(task_title, task_desc, task_deadLine,task_creation_time)
    }

    suspend fun deleteTask(task_id:Int) = datasource.deleteTask(task_id)

    suspend fun updateTask(task: Task) = datasource.updateTask(task)



    }