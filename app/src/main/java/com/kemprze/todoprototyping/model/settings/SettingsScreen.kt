package com.kemprze.todoprototyping.model.settings

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kemprze.todoprototyping.data.DarkModePreferences
import com.kemprze.todoprototyping.ui.theme.AppFont
import com.kemprze.todoprototyping.ui.theme.AppTheme
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
            ) }
        ) {
            innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Text(
                    text = "Theme",
                    style = MaterialTheme.typography.titleMedium
                )
                val isDark = isSystemInDarkTheme()
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AppTheme.entries.forEach {
                        theme ->
                        FilterChip(
                            selected = appTheme == theme,
                            onClick = { settingsViewModel.saveTheme(theme) },
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
                }
            }
        }
    }