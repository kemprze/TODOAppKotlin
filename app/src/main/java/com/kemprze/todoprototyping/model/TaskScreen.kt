package com.kemprze.todoprototyping.model
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kemprze.todoprototyping.data.model.DataSource
import com.kemprze.todoprototyping.data.model.simpleTask
import com.kemprze.todoprototyping.model.tasks.TasksViewModel
import com.kemprze.todoprototyping.ui.theme.TODOPrototypingTheme

@Composable
fun TaskScreen(modifier: Modifier = Modifier,
               tasksViewModel: TasksViewModel = viewModel()
) {
    val taskUiState by tasksViewModel.uiState.collectAsState()

    TaskList(
        taskList = taskUiState.tasks,
        modifier = modifier
    )
}
@Composable
fun TaskList(taskList: List<simpleTask>, modifier: Modifier = Modifier) {

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
    ) {
        items(taskList) {
            task -> TaskCard(name = task.taskName, description = task.taskDescription)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TaskListPreview() {
    TODOPrototypingTheme {
        Scaffold { innerPadding ->
            TaskScreen()
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TaskListPreviewDark() {
    TODOPrototypingTheme(darkTheme = true) {
        TaskScreen()
    }
}