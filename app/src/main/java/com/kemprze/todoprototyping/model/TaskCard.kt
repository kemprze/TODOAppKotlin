package com.kemprze.todoprototyping.model

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kemprze.todoprototyping.ui.theme.TODOPrototypingTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun DetailsRow(dueDate: LocalDate?, modifier: Modifier = Modifier) {
    if (dueDate != null) {
        val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        val formattedDataString = dueDate.format(formatter)
    }

    Column(modifier = Modifier
        .padding(8.dp)
        .animateContentSize(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioNoBouncy,
                stiffness = Spring.StiffnessMedium
            )
        )
    ) {
        if (dueDate != null) Text(
            text = "Due on: $dueDate",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
fun TaskCard(name: String, description: String, dueDate: LocalDate?, modifier: Modifier = Modifier) {
    var details by remember { mutableStateOf(false) }
        Card(onClick = {details = !details}) {
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(8.dp)) {
                TaskNameDescription(name, description, modifier = Modifier.weight(2f))
                TaskCheckbox(modifier = Modifier.weight(1f))
            }
            if (details) DetailsRow(dueDate = dueDate)
        }
}

@Composable
fun TaskNameDescription(name: String, description: String, modifier: Modifier = Modifier) {
    Column(modifier) {
        Text(
            text = name,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
        )
        Text(text = description,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 4.dp))
    }
}

@Composable
fun TaskCheckbox(modifier: Modifier = Modifier) {
    var checked by remember { mutableStateOf(true) }
    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = { checked = !checked }
        )
    }
}

@Preview
@Composable
fun TaskCardPreview() {
    TODOPrototypingTheme {
        TaskCard(name = "Throw trash out", description = "The trashcan is overflowing", dueDate = LocalDate.of(2025, 10, 2))
    }
}

@Preview()
@Composable
fun TaskCardPreviewDark() {
    TODOPrototypingTheme(darkTheme = true) {
        TaskCard(name = "Throw trash out", description = "The trashcan is overflowing", dueDate = LocalDate.of(2025, 10, 2))
    }
}