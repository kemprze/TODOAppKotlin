package com.kemprze.todoprototyping.model

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kemprze.todoprototyping.data.model.Category
import com.kemprze.todoprototyping.data.model.Priority
import com.kemprze.todoprototyping.data.model.simpleTask
import com.kemprze.todoprototyping.ui.theme.TODOPrototypingTheme
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId

@Composable
fun AddTaskWizard(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    onAddClick: (simpleTask) -> Unit
) {
    val pageCount = 4
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 4 })
    var taskName by rememberSaveable { mutableStateOf("") }
    var taskDescription by rememberSaveable { mutableStateOf("")}
    var priority by rememberSaveable { mutableStateOf(Priority.NORMAL) }
    var category by rememberSaveable { mutableStateOf(Category.NONE) }
    var dueDate by rememberSaveable { mutableStateOf<Long?>(null) }
    var needsReminder by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Scaffold(modifier) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LinearProgressIndicator(
                progress = { (pagerState.currentPage + 1) / pageCount.toFloat() },
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
            )
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) {
                page ->
                when (page) {
                    0 -> WizardStepName(
                        taskName = taskName,
                        onTaskNameChange = { taskName = it }
                    )
                    1 -> WizardStepDetails(
                        taskDescription = taskDescription,
                        onTaskDescriptionChange = { taskDescription = it},
                        selectedCategory = category,
                        onCategorySelected = { category = it }
                    )
                    2 -> WizardStepWhen(
                        dueDateMillis = dueDate,
                        onDateSelected = { dueDate = it },
                        needsReminder = needsReminder,
                        onReminderChange = { needsReminder = it }
                    )
                    3 -> WizardStepImportance(
                        priority = priority,
                        onPriorityChange = { priority = it }
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Back button
                IconButton(
                    onClick = {
                        if (pagerState.currentPage > 0) {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                            }
                        } else {
                            onNavigateBack()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }

                // Skip button — hidden on last page
                if (pagerState.currentPage < pageCount - 1) {
                    TextButton(onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }) {
                        Text("Skip")
                    }
                }

                // Next / Confirm button
                Button(onClick = {
                    if (pagerState.currentPage < pageCount - 1) {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    } else {
                        val newTask = simpleTask(
                            taskName = taskName,
                            taskDescription = taskDescription,
                            priority = priority,
                            category = category,
                            dueDate = dueDate?.let {
                                Instant.ofEpochMilli(it)
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate()
                            },
                            needsReminder = needsReminder
                        )
                        onAddClick(newTask)
                    }
                }) {
                    Text(if (pagerState.currentPage < pageCount - 1) "Next" else "Done")
                }
            }
        }
    }
}

@Composable
fun WizardStepName(
    taskName: String,
    onTaskNameChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.BottomEnd
        ) {
            Text(
                text = "What do you need to do?",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(32.dp)
            )
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(24.dp)
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.TopCenter
        ) {
            OutlinedTextField(
                value = taskName,
                onValueChange = onTaskNameChange,
                label = { Text("Task name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        }
    }
}

@Composable
fun WizardStepDetails(
    taskDescription: String,
    onTaskDescriptionChange: (String) -> Unit,
    selectedCategory: Category,
    onCategorySelected: (Category) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .weight(0.8f)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.BottomEnd
        ) {
            Column(modifier = Modifier.padding(32.dp)) {
                Text(
                    text = "Any details?",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(16.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(Category.entries) {
                        cat ->
                        FilterChip(
                            selected = selectedCategory == cat,
                            onClick = { onCategorySelected(cat) },
                            label = { Text("${stringResource(id = cat.categoryImageRes)} ${stringResource(id = cat.categoryNameRes)}") }
                        )
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.TopCenter
        ) {
            OutlinedTextField(
                value = taskDescription,
                onValueChange = onTaskDescriptionChange,
                label = { Text("Description (optional)") },
                modifier = Modifier
                    .padding(32.dp)
                    .fillMaxWidth(),
                minLines = 3,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                    unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                    focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f),
                    focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f),
                    cursorColor = MaterialTheme.colorScheme.onPrimary,
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                )
            )
        }
    }
}

@Composable
fun WizardStepWhen(
    dueDateMillis: Long?,
    onDateSelected: (Long?) -> Unit,
    needsReminder: Boolean,
    onReminderChange: (Boolean) -> Unit
) {
    val datePickerState = rememberDatePickerState()
    LaunchedEffect(
        datePickerState.selectedDateMillis
    ) {
        onDateSelected(datePickerState.selectedDateMillis)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .weight(0.4f)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.BottomStart
        ) {
            Text(
                text = "When do you need\nto do this?",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(32.dp)
            )
        }

        Column(
            modifier = Modifier
                .weight(1.6f)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(horizontal = 16.dp)
        ) {
            DatePicker(
                state = datePickerState,
                colors = DatePickerDefaults.colors(
                    containerColor = Color.Transparent,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    headlineContentColor = MaterialTheme.colorScheme.onPrimary,
                    weekdayContentColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                    dayContentColor = MaterialTheme.colorScheme.onPrimary,
                    selectedDayContainerColor = MaterialTheme.colorScheme.onPrimary,
                    selectedDayContentColor = MaterialTheme.colorScheme.primary,
                    todayContentColor = MaterialTheme.colorScheme.onPrimary,
                    todayDateBorderColor = MaterialTheme.colorScheme.onPrimary,
                    navigationContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                showModeToggle = false
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Remind me",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Switch(
                    checked = needsReminder,
                    onCheckedChange = onReminderChange,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colorScheme.primary,
                        checkedTrackColor = MaterialTheme.colorScheme.onPrimary,
                        uncheckedThumbColor = MaterialTheme.colorScheme.onPrimary,
                        uncheckedTrackColor = Color.Transparent,
                        uncheckedBorderColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        }
    }
}

@Composable
fun WizardStepImportance(
    priority: Priority,
    onPriorityChange: (Priority) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .weight(0.8f)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.BottomEnd
        ) {
            Text(
                text = "How much does\nthis matter?",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(36.dp)
            )
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.TopStart
        ) {
            Column(
                modifier = Modifier.padding(32.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Priority.entries.forEach {
                    p -> FilterChip(
                        selected = priority == p,
                        onClick = { onPriorityChange(p) },
                        label = {
                            Text("${p.emoji} ${p.label}")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = Color.Transparent,
                            labelColor = MaterialTheme.colorScheme.onPrimary,
                            selectedContainerColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f),
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = priority == p,
                            borderColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f),
                            selectedBorderColor = MaterialTheme.colorScheme.onPrimary,
                        )
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddTaskWizardPreview() {
    TODOPrototypingTheme {
        AddTaskWizard(onNavigateBack = {}, onAddClick = {})
    }
}