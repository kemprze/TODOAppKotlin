package com.kemprze.todoprototyping.data.repository

import com.kemprze.todoprototyping.data.model.DataSource
import com.kemprze.todoprototyping.data.model.simpleTask
import kotlinx.coroutines.delay

class TaskRepository {
    suspend fun getTasks(): List<simpleTask> {
        delay(1000)
        return DataSource.sampleTaskList
    }

    suspend fun getCompletedTasks(): List<simpleTask> {
        delay(1000)
        return DataSource.simpleCompletedTaskList
    }
}