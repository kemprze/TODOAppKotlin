package com.kemprze.todoprototyping.model.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kemprze.todoprototyping.data.model.Category
import com.kemprze.todoprototyping.data.model.DataSource
import com.kemprze.todoprototyping.data.repository.TaskRepository
import com.kemprze.todoprototyping.data.model.simpleTask
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

data class TasksUiState(
    val tasks: List<simpleTask> = DataSource.sampleTaskList,
    val completedTasks: List<simpleTask> = DataSource.simpleCompletedTaskList,
    val isLoading: Boolean = false
)
class TasksViewModel: ViewModel() {
    private val taskRepository = TaskRepository()
    private val _uiState = MutableStateFlow(TasksUiState())
    val uiState: StateFlow<TasksUiState> = _uiState.asStateFlow()

    init {
        loadTasks()
    }

    private fun loadTasks() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val tasks = taskRepository.getTasks()
            val completedTasks = taskRepository.getCompletedTasks()

            _uiState.value = TasksUiState(tasks = tasks, completedTasks = completedTasks, isLoading = false)
        }
    }

    fun onTaskAdded(taskName: String,
                    taskDescription: String,
                    isImportant: Boolean,
                    dueDate: LocalDate?,
                    needsReminder: Boolean,
                    remindMe: LocalDate?,
                    category: Category) {
        val newTask = simpleTask(
            taskName = taskName,
            taskDescription = taskDescription,
            isImportant = isImportant,
            dueDate = dueDate,
            remindMe = if (needsReminder) remindMe else null,
            createdOn = LocalDate.now(),
            category = category,
            isCompleted = false,
        )

        _uiState.update { currentState ->
            currentState.copy(
                tasks = currentState.tasks + newTask
            )
        }
    }

    fun onTaskCompleted(task: simpleTask, isCompleted: Boolean) {
        _uiState.update { currentState ->
            val sourceList = if (isCompleted) currentState.tasks else currentState.completedTasks
            val targetList = if (isCompleted) currentState.completedTasks else currentState.tasks

            val taskToMove = sourceList.find { it == task }
            if (taskToMove != null) {
                val newSource = sourceList - taskToMove
                val newTarget = targetList + taskToMove.copy(isCompleted = isCompleted)

                currentState.copy(
                    tasks = if (isCompleted) newSource else newTarget,
                    completedTasks = if (isCompleted) newTarget else newSource
                )
            } else {
                currentState
            }
        }
    }

}