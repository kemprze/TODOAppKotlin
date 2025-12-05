package com.kemprze.todoprototyping.model
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kemprze.todoprototyping.data.model.simpleTask
import com.kemprze.todoprototyping.model.tasks.TasksViewModel
import com.kemprze.todoprototyping.ui.theme.TODOPrototypingTheme

@Composable
fun TaskScreen(
    modifier: Modifier = Modifier,
               tasksViewModel: TasksViewModel = viewModel()
) {
    val taskUiState by tasksViewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            MainTaskScreenAppBar()
        }
    ) { innerPadding ->
        TaskList(
            incompleteTasks = taskUiState.tasks,
            completeTasks = taskUiState.completedTasks,
            onTaskCompleted = { task, isCompleted -> tasksViewModel.onTaskCompleted(task, isCompleted)},
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTaskScreenAppBar(modifier: Modifier = Modifier) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
        title = {
            Text("Sample App Bar View")
        },
        modifier = modifier
    )
}
@Composable
fun TaskList(incompleteTasks: List<simpleTask>, completeTasks: List<simpleTask>, onTaskCompleted: (simpleTask, Boolean) -> Unit, modifier: Modifier = Modifier) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
        modifier = modifier
    ) {
        items(incompleteTasks, key = {it.taskName}) {
            task -> TaskCard(task = task, onTaskCompleted = onTaskCompleted)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TaskListPreview() {
    TODOPrototypingTheme {
        TaskScreen()
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TaskListPreviewDark() {
    TODOPrototypingTheme(darkTheme = true) {
        TaskScreen()
    }
}