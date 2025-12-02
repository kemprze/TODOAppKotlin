package com.kemprze.todoprototyping.data.model

import androidx.annotation.DrawableRes
import java.time.LocalDate

data class simpleTask(
    var taskName: String,
    var taskDescription: String,
    @DrawableRes var taskImage: Int? = null,
    var isImportant: Boolean = false,
    var dueDate: LocalDate? = null
)