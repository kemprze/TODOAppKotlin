package com.kemprze.todoprototyping.data.repository

import com.kemprze.todoprototyping.data.TaskDao
import com.kemprze.todoprototyping.data.model.simpleTask
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {
     fun getTasks(): Flow<List<simpleTask>> {
        return taskDao.getAllTasks()
    }

    suspend fun insertTask(task: simpleTask) {
        taskDao.insertTask(task)
    }

    suspend fun updateTask(task: simpleTask) {
        taskDao.updateTask(task)
    }
}