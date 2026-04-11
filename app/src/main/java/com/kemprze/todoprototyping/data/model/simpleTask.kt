package com.kemprze.todoprototyping.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.time.LocalDate
import java.util.UUID

@Entity(tableName = "tasks")
@TypeConverters(Converters::class)
data class simpleTask(
    @PrimaryKey var id: String = UUID.randomUUID().toString(),
    var taskName: String,
    var taskDescription: String = "",
    var priority: Priority = Priority.NORMAL,
    var dueDate: LocalDate? = null,
    var needsReminder: Boolean = false,
    var remindMe: LocalDate? = null,
    var createdOn: LocalDate? = LocalDate.now(),
    var category: Category = Category.NONE,
    var isCompleted: Boolean = false,
    var duration: Duration = Duration.MEDIUM
)