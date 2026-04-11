package com.kemprze.todoprototyping.model
import android.R.attr.text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kemprze.todoprototyping.R
import com.kemprze.todoprototyping.data.model.simpleTask
import com.kemprze.todoprototyping.model.tasks.TasksViewModel
import com.kemprze.todoprototyping.ui.theme.TODOPrototypingTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import com.kemprze.todoprototyping.data.DarkModePreferences

@Composable
fun TaskScreen(
        modifier: Modifier = Modifier,
        tasksViewModel: TasksViewModel = viewModel(),
        onNavigateToAddTask: () -> Unit,
        onNavigateToSettings: () -> Unit
) {

    val taskUiState by tasksViewModel.uiState.collectAsState()
    var currentList by remember { mutableStateOf(ListTypes.INCOMPLETE) }

    Scaffold(
        topBar = {
            MainTaskScreenAppBar(
                onAddClick = onNavigateToAddTask,
                onSettingsClick = onNavigateToSettings
            )
        },
        bottomBar = { MainTaskScreenBottomAppBar(
            onListTypeChange = {
                    newListType -> currentList = newListType
                },
            currentList = currentList
            )
        }
    ) { innerPadding ->
        TaskList(
            currentListType = currentList,
            incompleteTasks = taskUiState.tasks,
            completeTasks = taskUiState.completedTasks,
            onTaskCompleted = { task, isCompleted -> tasksViewModel.onTaskCompleted(task, isCompleted)},
            onTaskDeleted = { task -> tasksViewModel.onTaskDeleted(task) },
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTaskScreenAppBar(modifier: Modifier = Modifier, onAddClick: () -> Unit,
                         onSettingsClick: () -> Unit) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
        title = {
            Text("Tasks")
        },
        actions = {
            IconButton(
                onClick = onAddClick
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add new task"
                )
            }
            IconButton(
                onClick = onSettingsClick
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings"
                )
            }
        },
        modifier = modifier
    )
}

@Composable
fun MainTaskScreenBottomAppBar(onListTypeChange: (ListTypes) -> Unit,
                               currentList: ListTypes,
                               modifier: Modifier = Modifier) {
    BottomAppBar(modifier) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SingleChoiceSegmentedButtonRow() {
                ListTypes.entries.forEachIndexed { index, listType ->
                    SegmentedButton(
                        selected = listType == currentList,
                        onClick = { onListTypeChange(listType) },
                        shape = SegmentedButtonDefaults.itemShape(index, ListTypes.entries.size),
                        icon = {},
                        label = { Text(listType.name.lowercase().replaceFirstChar { it.titlecase() }) }
                    )
                }
            }
        }
    }
}

@Composable
fun TaskList(incompleteTasks: List<simpleTask>,
             completeTasks: List<simpleTask>,
             currentListType: ListTypes,
             onTaskCompleted: (simpleTask, Boolean) -> Unit,
             onTaskDeleted: (simpleTask) -> Unit,
             modifier: Modifier = Modifier) {

    val currentListItems = when (currentListType) {
        ListTypes.INCOMPLETE -> incompleteTasks
        ListTypes.COMPLETE -> completeTasks
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(dimensionResource(R.dimen.padding_medium)),
        modifier = modifier
    ) {
        items(currentListItems, key = {it.id}) {
            task -> TaskCard(task = task,
            onTaskCompleted = onTaskCompleted,
            onTaskDeleted = onTaskDeleted,
                modifier = Modifier.animateItem())
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TaskListPreview() {
    TODOPrototypingTheme {
        TaskScreen(
            onNavigateToAddTask = { },
            onNavigateToSettings = { }
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TaskListPreviewDark() {
    TODOPrototypingTheme(darkTheme = true) {
        TaskScreen(
            onNavigateToAddTask = { },
            onNavigateToSettings = { }
        )
    }
}