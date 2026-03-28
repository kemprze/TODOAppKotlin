package com.kemprze.todoprototyping.data.model

import androidx.room.TypeConverter
import java.time.LocalDate
class Converters {

    @TypeConverter
    fun localDateToString(date: LocalDate?): String? {
        return date?.toString()
    }

    @TypeConverter
    fun stringToLocalDate(value: String?): LocalDate? {
        return value?.let { LocalDate.parse(it)}
    }

    @TypeConverter
    fun categoryToString(category: Category): String {
        return category.name
    }

    @TypeConverter
    fun stringToCategory(value: String): Category {
        return Category.valueOf(value)
    }

    @TypeConverter
    fun priorityToInt(priority: Priority): Int {
        return priority.level
    }

    @TypeConverter
    fun intToPriority(value: Int): Priority {
        return Priority.entries.first { it.level == value }
    }
}