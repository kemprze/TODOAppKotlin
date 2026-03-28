package com.kemprze.todoprototyping.model.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kemprze.todoprototyping.data.model.Category
import com.kemprze.todoprototyping.data.model.Priority
import com.kemprze.todoprototyping.data.repository.TaskRepository
import com.kemprze.todoprototyping.data.model.simpleTask
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

data class TasksUiState(
    val tasks: List<simpleTask> = emptyList(),
    val completedTasks: List<simpleTask> = emptyList(),
    val isLoading: Boolean = false
)
class TasksViewModel(private val taskRepository: TaskRepository): ViewModel() {
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
                    dueDate: LocalDate?,
                    needsReminder: Boolean,
                    remindMe: LocalDate?,
                    category: Category) {
        val newTask = simpleTask(
            taskName = taskName,
            taskDescription = taskDescription,
            priority = priority,
            dueDate = dueDate,
            remindMe = if (needsReminder) remindMe else null,
            createdOn = LocalDate.now(),
            category = category,
            isCompleted = false,
        )

        viewModelScope.launch {
            taskRepository.insertTask(newTask)
        }
    }

    fun onTaskCompleted(task: simpleTask, isCompleted: Boolean) {
        viewModelScope.launch {
            taskRepository.updateTask(task.copy(isCompleted = isCompleted))
        }
    }

}