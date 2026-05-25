package com.example.myapplication.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myapplication.ui.theme.AppThemeMode

@Composable
fun ThemeSelector(
    onThemeSelected: (AppThemeMode) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        AppButton(
            text = "Azul"
        ) {
            onThemeSelected(AppThemeMode.BLUE)
        }
        AppButton(
            text = "Verde"
        ) {
            onThemeSelected(AppThemeMode.GREEN)
        }
        AppButton(
            text = "Morado"
        ) {
            onThemeSelected(AppThemeMode.PURPLE)
        }
        AppButton(
            text = "Naranja"
        ) {
            onThemeSelected(AppThemeMode.ORANGE) // nueva opcion
        }
    }
}