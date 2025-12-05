package com.kemprze.todoprototyping.data.model

data class simpleTaskList(
    val taskList: MutableList<simpleTask> = mutableListOf(),
    val finishedTaskList: MutableList<simpleTask> = mutableListOf()
)
