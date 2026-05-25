package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.myapplication.navigation.AppNavigation
import com.example.myapplication.ui.theme.AppThemeMode
import com.example.myapplication.ui.theme.ModularStoreTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var currentTheme by remember {
                mutableStateOf(AppThemeMode.BLUE)
            }
            ModularStoreTheme(
                themeMode = currentTheme
            ) {
                AppNavigation {
                    currentTheme = when(it) {
                        "GREEN" -> AppThemeMode.GREEN
                        "PURPLE" -> AppThemeMode.PURPLE
                        "ORANGE" -> AppThemeMode.ORANGE
                        else -> AppThemeMode.BLUE
                    }
                }
            }
        }
    }
}
