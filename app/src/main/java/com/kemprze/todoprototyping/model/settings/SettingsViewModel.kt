package com.kemprze.todoprototyping.model.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kemprze.todoprototyping.data.DarkModePreferences
import com.kemprze.todoprototyping.data.SettingsDataStore
import com.kemprze.todoprototyping.ui.theme.AppFont
import com.kemprze.todoprototyping.ui.theme.AppTheme
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val settingsDataStore = SettingsDataStore(application)

    val themeFlow = settingsDataStore.themeFlow
    val fontFlow = settingsDataStore.fontFlow
    val darkModeFlow = settingsDataStore.darkModeFlow
    val dynamicColorFlow = settingsDataStore.dynamicColorFlow

    fun saveTheme(theme: AppTheme) {
        viewModelScope.launch {
            settingsDataStore.saveTheme(theme)
        }
    }
    fun saveFont(font: AppFont) {
        viewModelScope.launch {
            settingsDataStore.saveFont(font)
        }
    }

    fun saveDarkMode(darkMode: DarkModePreferences) {
        viewModelScope.launch {
            settingsDataStore.saveDarkMode(darkMode)
        }
    }

    fun saveDynamicColor(isEnabled: Boolean) {
        viewModelScope.launch {
            settingsDataStore.saveDynamicColor(isEnabled)
        }
    }

}