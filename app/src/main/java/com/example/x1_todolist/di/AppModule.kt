package com.example.x1_todolist.di

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.room.Room
import com.example.x1_todolist.data.datasource.DataSource
import com.example.x1_todolist.data.repository.TaskRepository
import com.example.x1_todolist.room.TaskDao
import com.example.x1_todolist.room.TaskDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideTaskDao(@ApplicationContext context: Context): TaskDao {
        val db =Room.databaseBuilder(
            context.applicationContext,
            TaskDataBase::class.java,
            "task.db")
            .build()
        return db.TaskDao()
    }

    @Provides
    @Singleton
    fun provideDataSource(taskDao: TaskDao) : DataSource {
        return DataSource(taskDao)
    }

    @Provides
    @Singleton
    fun provideTaskRepository(datasource: DataSource) : TaskRepository {
        return TaskRepository(datasource)
    }





}