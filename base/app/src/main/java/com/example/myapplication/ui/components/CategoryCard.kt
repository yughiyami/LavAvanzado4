package com.example.myapplication.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// nueva clase
@Composable
fun CategoryCard(
    categoryName: String,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    AppButton(
        text = if (isSelected) {
            "(*) $categoryName"
        } else {
            categoryName
        },
        modifier = Modifier.padding(2.dp)
    ) {
        onClick()
    }
}