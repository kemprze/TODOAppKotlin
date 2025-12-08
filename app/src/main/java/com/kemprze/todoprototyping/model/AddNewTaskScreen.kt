package com.kemprze.todoprototyping.model

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.kemprze.todoprototyping.R
import com.kemprze.todoprototyping.data.model.Category
import com.kemprze.todoprototyping.data.model.simpleTask
import com.kemprze.todoprototyping.ui.theme.TODOPrototypingTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun AddNewTaskScreen(modifier: Modifier = Modifier) {
    val LocalDateSaver = Saver<LocalDate?, String> (
            save = { it?.toString() },
            restore = { it?.let {LocalDate.parse(it) }
            }
        )

    // savers used to move the values to the UiState
    var isCategoryMenuExpanded by remember { mutableStateOf(false) }
    var isDatePickerExpanded by remember { mutableStateOf(false) }
    
    var taskName by rememberSaveable { mutableStateOf("") }
    var taskDescription by rememberSaveable { mutableStateOf("") }
    var isImportant by rememberSaveable { mutableStateOf(false) }
    var dueDate by rememberSaveable ( stateSaver = LocalDateSaver) { mutableStateOf(null) }
    var createdOn by rememberSaveable ( stateSaver = LocalDateSaver ){ mutableStateOf(LocalDate.now()) }
    var category by rememberSaveable { mutableStateOf(Category.NONE) }

    Scaffold(modifier) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            BasicInformationRow(
                taskName = taskName,
                onTaskNameChange = { taskName = it },
                taskDescription = taskDescription,
                onTaskDescriptionChange = {taskDescription = it },
                isImportant = isImportant,
                onImportantChange = { isImportant = it },
                isCategoryMenuExpanded = isCategoryMenuExpanded,
                onCategorySelected = { category = it },
                selectedCategory = category,
                onExpandedChange = { isCategoryMenuExpanded = it },
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_medium))
                    .fillMaxWidth()
            )
            TaskDatePicker(
                dueDate = dueDate,
                onDateSelected = { dueDate = it },
                isDatePickerExpanded = isDatePickerExpanded,
                onShowDatePickerChange = { isDatePickerExpanded = it },
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
            )
            ButtonRow(
                onAddClick = {},
                modifier = Modifier
            )
        }
    }
}

@Composable
fun TaskDatePicker(
    modifier: Modifier = Modifier,
    dueDate: LocalDate?, // currently selected date
    onDateSelected: (LocalDate) -> Unit, // callback when a date is selected in the picker
    isDatePickerExpanded: Boolean, // boolean state showing/hiding the value
    onShowDatePickerChange: (Boolean) -> Unit, // callback to change the dialog state
    ) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
    ) {
        OutlinedTextField(
            value = dueDate?.format(DateTimeFormatter.ISO_LOCAL_DATE) ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text("Due date") },
            placeholder = { Text("Select a due date") },
            modifier = Modifier
                .pointerInteropFilter { false }
        )
        IconButton( onClick = { onShowDatePickerChange(true) }) {
            Icon(
                imageVector = androidx.compose.material.icons.Icons.Default.DateRange,
                contentDescription = "Open calendar") // to fix, looks ugly and uncentered
        }
    }

    if (isDatePickerExpanded) {
        val datePickerState = rememberDatePickerState()

        DatePickerDialog(
            onDismissRequest = { onShowDatePickerChange(false) },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let {
                            millis ->
                                val selectedDate = java.time.Instant.ofEpochMilli(millis)
                                    .atZone(java.time.ZoneId.systemDefault())
                                    .toLocalDate()
                            onDateSelected(selectedDate)
                        }
                        onShowDatePickerChange(false)
                    }
                ) {
                    Text(
                        text = "Confirm"
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { onShowDatePickerChange(false) }
                ) {
                    Text(
                        text = "Cancel"
                    )
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicInformationRow(
    modifier: Modifier = Modifier,
    taskName: String,
    taskDescription: String,
    isImportant: Boolean,
    onImportantChange: (Boolean) -> Unit,
    onTaskNameChange: (String) -> Unit,
    onTaskDescriptionChange: (String) -> Unit,
    onExpandedChange: (Boolean) -> Unit,
    isCategoryMenuExpanded: Boolean,
    selectedCategory: Category,
    onCategorySelected: (Category) -> Unit
) {
    Column(modifier) {
        OutlinedTextField(
            label = {
                Text("Name")
                    },
            value = taskName,
            onValueChange = onTaskNameChange,
            modifier = Modifier
        )

        Spacer(modifier = Modifier
            .height(dimensionResource(R.dimen.padding_medium))
            .fillMaxWidth())

        OutlinedTextField(
            label = {
                Text("Description")
            },
            value = taskDescription,
            onValueChange = onTaskDescriptionChange,
            modifier = Modifier
        )

        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.Center, 
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Set as important? ")
            Switch(
                checked = isImportant,
                onCheckedChange = onImportantChange,
                enabled = true,
                )
        }

        ExposedDropdownMenuBox(
            expanded = isCategoryMenuExpanded,
            onExpandedChange = onExpandedChange,
            modifier = Modifier,
        ) {
            OutlinedTextField(
                modifier = Modifier.menuAnchor(),
                readOnly = true,
                value = selectedCategory.name
                    .lowercase()
                    .replace('_', ' ')
                    .replaceFirstChar {it.titlecase()},
                onValueChange = { },
                label = { Text("Category") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = isCategoryMenuExpanded
                    )
                }
            )

            ExposedDropdownMenu(
                expanded = isCategoryMenuExpanded,
                onDismissRequest = { onExpandedChange(false) }
            ) {
                Category.values().forEach { categoryOption ->
                    DropdownMenuItem(
                        text = { Text(categoryOption.name
                            .lowercase()
                            .replace('_', ' ')
                            .replaceFirstChar {it.titlecase() })},
                        onClick = {
                            onCategorySelected(categoryOption)
                            onExpandedChange(false)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ButtonRow(modifier: Modifier = Modifier, onAddClick: (simpleTask) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Button(onClick = {}) {
            Text(text = "Cancel")
        }
        Button(onClick = {}) {
            Text(text = "Add new task")
        }
    }
}

@Preview
@Composable
fun AddNewTaskScreenPreview() {
    TODOPrototypingTheme {
        AddNewTaskScreen()
    }
}