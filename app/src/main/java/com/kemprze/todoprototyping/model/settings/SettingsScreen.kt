package com.kemprze.todoprototyping.model.settings

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kemprze.todoprototyping.data.DarkModePreferences
import com.kemprze.todoprototyping.ui.theme.AppFont
import com.kemprze.todoprototyping.ui.theme.AppTheme
import com.kemprze.todoprototyping.ui.theme.fontFamilyFor
import com.kemprze.todoprototyping.ui.theme.themePrimaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    settingsViewModel: SettingsViewModel,
    onNavigateBack: () -> Unit
) {
    val appTheme by settingsViewModel.themeFlow.collectAsState(initial = AppTheme.SCARLET)
    val appFont by settingsViewModel.fontFlow.collectAsState(initial = AppFont.LATO)
    val darkMode by settingsViewModel.darkModeFlow.collectAsState(initial = DarkModePreferences.SYSTEM)
    val dynamicColor by settingsViewModel.dynamicColorFlow.collectAsState(initial = false)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            ) },
        ) {
            innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Color mode",
                    style = MaterialTheme.typography.titleMedium
                )
                SingleChoiceSegmentedButtonRow() { 
                    DarkModePreferences.entries.forEachIndexed {
                        index, mode ->
                        SegmentedButton(
                            selected = darkMode == mode,
                            onClick = { settingsViewModel.saveDarkMode(mode) },
                            shape = SegmentedButtonDefaults.itemShape(index, DarkModePreferences.entries.size),
                            label = { Text(mode.name.lowercase().replaceFirstChar { it.titlecase() }) }
                        )
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Dynamic color",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Switch(
                        checked = dynamicColor,
                        onCheckedChange = { settingsViewModel.saveDynamicColor(it) },
                    )
                }

                if (!dynamicColor) {
                    Text(
                        text = "Theme",
                        style = MaterialTheme.typography.titleMedium
                    )
                    val isDark = isSystemInDarkTheme()
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        AppTheme.entries.forEach { theme ->
                            ThemeChip(
                                theme = theme,
                                selected = appTheme == theme,
                                isDark = isDark,
                                onClick = { settingsViewModel.saveTheme(theme) }
                            )
                        }
                    }
                }

                Text(
                    text = "Font",
                    style = MaterialTheme.typography.titleMedium
                )
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AppFont.entries.forEach {
                        font ->
                        FontCard(
                            font = font,
                            selected = appFont == font,
                            onClick = { settingsViewModel.saveFont(font) }
                        )
                    }
                }
            }
        }
    }

@Composable
private fun FontCard(
    font: AppFont,
    selected: Boolean,
    onClick: () -> Unit
) {
    val label = when (font) {
        AppFont.PLAYFAIR -> "Playfair"
        AppFont.LORA -> "Lora"
        AppFont.MONTSERRAT -> "Montserrat"
        AppFont.LATO -> "Lato"
        AppFont.ATKINSON -> "Atkinson"
        AppFont.COURIER_PRIME -> "Courier"
    }

    FilterChip(
        selected = selected,
        onClick = onClick,
        label = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Aa",
                    fontFamily = fontFamilyFor(font),
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = label,
                    fontFamily = fontFamilyFor(font),
                    fontSize = 10.sp,
                    textAlign = TextAlign.Center
                )
            }
        },
        modifier = Modifier
            .width(92.dp)
            .height(76.dp)
            .padding(top = 4.dp)
    )
}

@Composable
private fun ThemeChip(
    theme: AppTheme,
    isDark: Boolean,
    selected: Boolean,
    onClick: () -> Unit
    ) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = { },
        colors = FilterChipDefaults.filterChipColors(
            containerColor = themePrimaryColor(theme, isDark),
            selectedContainerColor = themePrimaryColor(theme, isDark)
        ),
        modifier = Modifier
            .width(48.dp)
            .height(48.dp)
    )
}