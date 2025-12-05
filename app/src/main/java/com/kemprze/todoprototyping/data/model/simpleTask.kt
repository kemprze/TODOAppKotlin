package com.kemprze.todoprototyping.data.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import java.time.LocalDate

data class simpleTask(
    var taskName: String,
    var taskDescription: String,
    var isImportant: Boolean = false,
    var dueDate: LocalDate? = null,
    var remindMe: Boolean = false,
    var createdOn: LocalDate? = LocalDate.now(),
    var category: Category = Category.NONE,
    var isCompleted: Boolean = false
)