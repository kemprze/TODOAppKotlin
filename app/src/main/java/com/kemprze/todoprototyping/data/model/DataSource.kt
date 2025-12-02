package com.kemprze.todoprototyping.data.model

import java.time.LocalDate

object DataSource {
    val sampleTaskList: List<simpleTask> = listOf(
        simpleTask("Buy groceries", "Milk, Bread, Cheese, Eggs", isImportant = true, dueDate = LocalDate.of(2025, 10, 2)),
        simpleTask("Call mom", "Wish her a happy birthday", isImportant = true, dueDate = LocalDate.of(2025, 7, 19)),
        simpleTask("Finish project report", "Due by Friday", isImportant = true, dueDate = LocalDate.of(2025, 4, 11)),
        simpleTask("Go for a run", "30 minutes at the park", dueDate = LocalDate.of(2025, 8, 5)),
        simpleTask("Read a chapter of a book", "Chapter 5 of 'The Hobbit'", dueDate = LocalDate.of(2025, 1, 22)),
        simpleTask("Water the plants", "They look thirsty", dueDate = LocalDate.of(2025, 9, 14)),
        simpleTask("Schedule dentist appointment", "Check for a 6-month cleaning", dueDate = LocalDate.of(2025, 11, 30)),
        simpleTask("Pay electricity bill", "Due on the 25th", dueDate = LocalDate.of(2025, 5, 25)),
        simpleTask("Organize the closet", "Donate old clothes", dueDate = LocalDate.of(2025, 2, 18)),
        simpleTask("Plan weekend trip", "Look for cabins in the mountains", dueDate = LocalDate.of(2025, 6, 7)),
        simpleTask("Learn a new recipe", "Try making paella", dueDate = LocalDate.of(2025, 12, 1)),
        simpleTask("Fix leaky faucet", "Buy a new washer", dueDate = LocalDate.of(2025, 3, 29)),
        simpleTask("Walk the dog", "He needs his evening walk", isImportant = true, dueDate = LocalDate.of(2025, 7, 1)),
        simpleTask("Update resume", "Add new skills and projects", dueDate = LocalDate.of(2025, 10, 15)),
        simpleTask("Clean the bathroom", "Scrub the tub and toilet", dueDate = LocalDate.of(2025, 4, 20)),
        simpleTask("Meditate for 10 minutes", "Use the Calm app", dueDate = LocalDate.of(2025, 8, 12)),
        simpleTask("Write a blog post", "Topic: 10 tips for better sleep", dueDate = LocalDate.of(2025, 2, 10)),
        simpleTask("Book flight tickets", "For the summer vacation", dueDate = LocalDate.of(2025, 6, 21)),
        simpleTask("Do laundry", "Whites and colors separately", dueDate = LocalDate.of(2025, 9, 3)),
        simpleTask("Take out the trash", "Recycling day tomorrow", dueDate = LocalDate.of(2025, 11, 5))
    )
}
