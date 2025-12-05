package com.kemprze.todoprototyping.data.model

import java.time.LocalDate

object DataSource {
    val sampleTaskList: List<simpleTask> = listOf(
        simpleTask("Buy groceries", "Milk, Bread, Cheese, Eggs", isImportant = true, dueDate = LocalDate.of(2025, 10, 2), createdOn = LocalDate.of(2024, 1, 15), category = Category.SHOPPING),
        simpleTask("Call mom", "Wish her a happy birthday", isImportant = true, dueDate = LocalDate.of(2025, 7, 19), createdOn = LocalDate.of(2024, 2, 20), category = Category.PERSONAL),
        simpleTask("Finish project report", "Due by Friday", isImportant = true, dueDate = LocalDate.of(2025, 4, 11), createdOn = LocalDate.of(2024, 3, 1), category = Category.WORK),
        simpleTask("Go for a run", "30 minutes at the park", dueDate = LocalDate.of(2025, 8, 5), createdOn = LocalDate.of(2024, 4, 5), category = Category.HEALTH),
        simpleTask("Read a chapter of a book", "Chapter 5 of 'The Hobbit'", dueDate = LocalDate.of(2025, 1, 22), createdOn = LocalDate.of(2024, 5, 10), category = Category.PERSONAL),
        simpleTask("Water the plants", "They look thirsty", dueDate = LocalDate.of(2025, 9, 14), createdOn = LocalDate.of(2024, 6, 12), category = Category.HOME),
        simpleTask("Schedule dentist appointment", "Check for a 6-month cleaning", dueDate = LocalDate.of(2025, 11, 30), createdOn = LocalDate.of(2024, 7, 18), category = Category.HEALTH),
        simpleTask("Pay electricity bill", "Due on the 25th", dueDate = LocalDate.of(2025, 5, 25), createdOn = LocalDate.of(2024, 8, 22), category = Category.FINANCE),
        simpleTask("Organize the closet", "Donate old clothes", dueDate = LocalDate.of(2025, 2, 18), createdOn = LocalDate.of(2024, 9, 30), category = Category.HOME),
        simpleTask("Plan weekend trip", "Look for cabins in the mountains", dueDate = LocalDate.of(2025, 6, 7), createdOn = LocalDate.of(2024, 10, 5), category = Category.PERSONAL),
        simpleTask("Learn a new recipe", "Try making paella", dueDate = LocalDate.of(2025, 12, 1), createdOn = LocalDate.of(2024, 11, 15), category = Category.OTHER),
        simpleTask("Fix leaky faucet", "Buy a new washer", dueDate = LocalDate.of(2025, 3, 29), createdOn = LocalDate.of(2024, 12, 20), category = Category.HOME),
        simpleTask("Walk the dog", "He needs his evening walk", isImportant = true, dueDate = LocalDate.of(2025, 7, 1), createdOn = LocalDate.of(2024, 1, 25), category = Category.PERSONAL),
        simpleTask("Update resume", "Add new skills and projects", dueDate = LocalDate.of(2025, 10, 15), createdOn = LocalDate.of(2024, 2, 28), category = Category.WORK),
        simpleTask("Clean the bathroom", "Scrub the tub and toilet", dueDate = LocalDate.of(2025, 4, 20), createdOn = LocalDate.of(2024, 3, 10), category = Category.HOME),
        simpleTask("Meditate for 10 minutes", "Use the Calm app", dueDate = LocalDate.of(2025, 8, 12), createdOn = LocalDate.of(2024, 4, 15), category = Category.HEALTH),
        simpleTask("Write a blog post", "Topic: 10 tips for better sleep", dueDate = LocalDate.of(2025, 2, 10), createdOn = LocalDate.of(2024, 5, 20), category = Category.WORK),
        simpleTask("Book flight tickets", "For the summer vacation", dueDate = LocalDate.of(2025, 6, 21), createdOn = LocalDate.of(2024, 6, 25), category = Category.PERSONAL),
        simpleTask("Do laundry", "Whites and colors separately", dueDate = LocalDate.of(2025, 9, 3), createdOn = LocalDate.of(2024, 7, 30), category = Category.HOME),
        simpleTask("Take out the trash", "Recycling day tomorrow", dueDate = LocalDate.of(2025, 11, 5), createdOn = LocalDate.of(2024, 8, 5), category = Category.HOME)
    )

    val simpleCompletedTaskList: List<simpleTask> = mutableListOf()
}
