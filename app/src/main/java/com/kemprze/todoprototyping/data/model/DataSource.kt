package com.kemprze.todoprototyping.data.model

object DataSource {
    val sampleTaskList: List<simpleTask> = listOf(
        simpleTask("Buy groceries", "Milk, Bread, Cheese, Eggs", isImportant = true),
        simpleTask("Call mom", "Wish her a happy birthday", isImportant = true),
        simpleTask("Finish project report", "Due by Friday", isImportant = true),
        simpleTask("Go for a run", "30 minutes at the park"),
        simpleTask("Read a chapter of a book", "Chapter 5 of 'The Hobbit'"),
        simpleTask("Water the plants", "They look thirsty"),
        simpleTask("Schedule dentist appointment", "Check for a 6-month cleaning"),
        simpleTask("Pay electricity bill", "Due on the 25th"),
        simpleTask("Organize the closet", "Donate old clothes"),
        simpleTask("Plan weekend trip", "Look for cabins in the mountains"),
        simpleTask("Learn a new recipe", "Try making paella"),
        simpleTask("Fix leaky faucet", "Buy a new washer"),
        simpleTask("Walk the dog", "He needs his evening walk", isImportant = true),
        simpleTask("Update resume", "Add new skills and projects"),
        simpleTask("Clean the bathroom", "Scrub the tub and toilet"),
        simpleTask("Meditate for 10 minutes", "Use the Calm app"),
        simpleTask("Write a blog post", "Topic: 10 tips for better sleep"),
        simpleTask("Book flight tickets", "For the summer vacation"),
        simpleTask("Do laundry", "Whites and colors separately"),
        simpleTask("Take out the trash", "Recycling day tomorrow")
    )
}
