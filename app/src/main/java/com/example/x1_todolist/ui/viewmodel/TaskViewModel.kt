package com.example.x1_todolist.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.x1_todolist.data.datasource.DataSource
import com.example.x1_todolist.data.entity.Task
import com.example.x1_todolist.data.repository.TaskRepository
import com.example.x1_todolist.room.TaskDataBase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TaskViewModel @Inject constructor(var repository: TaskRepository): ViewModel() {

    init {
        getAllTasks()
    }

    fun getAllTasks() = repository.getAllTasks()

    fun addTask(task_title : String,
                task_desc : String,
                task_deadLine : String,
                task_creation_time : Pair<String, String>) {
        viewModelScope.launch(Dispatchers.IO){
            repository.addTask(task_title, task_desc, task_deadLine, task_creation_time)
        }
    }

    fun deleteTask(task_id:Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTask(task_id)
            getAllTasks()
        }
    }

    fun updateTask(task: Task){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateTask(task)
        }
    }


}