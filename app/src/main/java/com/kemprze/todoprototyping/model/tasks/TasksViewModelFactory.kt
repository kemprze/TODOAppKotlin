package com.kemprze.todoprototyping.model.tasks

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kemprze.todoprototyping.data.TaskDatabase
import com.kemprze.todoprototyping.data.repository.TaskRepository

class TasksViewModelFactory(private val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val database = TaskDatabase.getDatabase(context)
        val repository = TaskRepository(database.taskDao())

        @Suppress("UNCHECKED_CAST")
        return TasksViewModel(repository, context.applicationContext as Application) as T
    }

}