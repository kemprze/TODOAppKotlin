package com.kemprze.todoprototyping.model.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kemprze.todoprototyping.data.model.DataSource
import com.kemprze.todoprototyping.data.repository.TaskRepository
import com.kemprze.todoprototyping.data.model.simpleTask
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class TasksUiState(
    val tasks: List<simpleTask> = DataSource.sampleTaskList,
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
            _uiState.value = TasksUiState(tasks = tasks, isLoading = false)
        }
    }
}