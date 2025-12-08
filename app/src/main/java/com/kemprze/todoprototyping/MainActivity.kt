package com.kemprze.todoprototyping

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kemprze.todoprototyping.model.AddNewTaskScreen
import com.kemprze.todoprototyping.model.TaskScreen
import com.kemprze.todoprototyping.model.tasks.TasksViewModel
import com.kemprze.todoprototyping.navigation.Screen
import com.kemprze.todoprototyping.ui.theme.TODOPrototypingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TODOPrototypingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberNavController()
    val tasksViewModel: TasksViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = Screen.TaskListScreen.route
    ) {
        composable(route = Screen.TaskListScreen.route) {
            TaskScreen(
                tasksViewModel = tasksViewModel,
                onNavigateToAddTask = {
                    navController.navigate(Screen.AddTaskScreen.route)
                })
        }
        composable(route = Screen.AddTaskScreen.route) {
            AddNewTaskScreen()
        }
    }
}

@Composable
@Preview
fun NavigationPreview() {
    TODOPrototypingTheme {
        AppNavigation()
    }
}