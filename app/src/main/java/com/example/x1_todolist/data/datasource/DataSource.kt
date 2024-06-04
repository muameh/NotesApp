package com.example.x1_todolist.data.datasource

import androidx.lifecycle.LiveData
import com.example.x1_todolist.data.entity.Task
import com.example.x1_todolist.room.TaskDao
import com.example.x1_todolist.utils.PairConverter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DataSource(var taskDao:TaskDao) {

    fun getAllTasks() = taskDao.getAllTasks()

    suspend fun addTask(task_title:String,
                        task_desc:String,
                        task_deadLine:String,
                        task_creation_time:Pair<String, String>) {
        val creationTime = PairConverter.fromPair(task_creation_time)
        val newTask = Task(0,task_title,task_desc,task_deadLine,creationTime)
        taskDao.addTask(newTask)
    }

    suspend fun deleteTask(task_id:Int) {
        val task = Task(task_id,"","","","")
        taskDao.deleteTask(task)
    }

    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }

}