package com.kemprze.todoprototyping.model.tasks

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kemprze.todoprototyping.data.model.Category
import com.kemprze.todoprototyping.data.model.Duration
import com.kemprze.todoprototyping.data.model.Priority
import com.kemprze.todoprototyping.data.model.ReminderWorker
import com.kemprze.todoprototyping.data.repository.TaskRepository
import com.kemprze.todoprototyping.data.model.simpleTask
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime

data class TasksUiState(
    val tasks: List<simpleTask> = emptyList(),
    val completedTasks: List<simpleTask> = emptyList(),
    val isLoading: Boolean = false
)
class TasksViewModel(private val taskRepository: TaskRepository,
                     application: Application): AndroidViewModel(application) {
    private val _uiState = MutableStateFlow(TasksUiState())
    val uiState: StateFlow<TasksUiState> = _uiState.asStateFlow()

    init {
        loadTasks()
    }

    private fun loadTasks() {
        viewModelScope.launch {
            taskRepository.getTasks().collect {allTasks ->
                _uiState.update { currentState ->
                    currentState.copy(
                        tasks = allTasks.filter { !it.isCompleted },
                        completedTasks = allTasks.filter { it.isCompleted },
                        isLoading = false
                    )
                }
            }
        }
    }

    fun onTaskAdded(taskName: String,
                    taskDescription: String,
                    priority: Priority,
                    dueDate: LocalDateTime?,
                    needsReminder: Boolean,
                    remindMe: LocalDateTime?,
                    category: Category,
                    duration: Duration) {
        val newTask = simpleTask(
            taskName = taskName,
            taskDescription = taskDescription,
            priority = priority,
            dueDate = dueDate,
            remindMe = if (needsReminder) remindMe else null,
            createdOn = LocalDateTime.now(),
            category = category,
            isCompleted = false,
            duration = duration
        )

        viewModelScope.launch {
            taskRepository.insertTask(newTask)

            if (needsReminder && newTask.remindMe != null) {
                val delay = java.time.temporal.ChronoUnit.MILLIS.between(
                    LocalDateTime.now(),
                    newTask.remindMe
                )

                val inputData = androidx.work.Data.Builder()
                    .putString("task_name", newTask.taskName)
                    .putString("task_id", newTask.id)
                    .build()

                val workRequest = androidx.work.OneTimeWorkRequestBuilder<ReminderWorker>()
                    .setInitialDelay(delay, java.util.concurrent.TimeUnit.MILLISECONDS)
                    .setInputData(inputData)
                    .build()

                androidx.work.WorkManager.getInstance(getApplication<Application>())
                    .enqueue(workRequest)
            }
        }
    }

    fun onTaskCompleted(task: simpleTask, isCompleted: Boolean) {
        viewModelScope.launch {
            taskRepository.updateTask(task.copy(isCompleted = isCompleted))
        }
    }

    fun onTaskDeleted(task: simpleTask) {
        viewModelScope.launch {
            taskRepository.deleteTask(task)
        }
    }

}