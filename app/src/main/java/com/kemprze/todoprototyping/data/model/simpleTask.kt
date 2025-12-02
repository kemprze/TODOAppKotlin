package com.kemprze.todoprototyping.data.model

import androidx.annotation.DrawableRes

data class simpleTask(
    var taskName: String,
    var taskDescription: String,
    @DrawableRes var taskImage: Int? = null,
    var isImportant: Boolean = false
)