package com.example.lavavanzado4.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.lavavanzado4.domain.model.Category

@Composable
fun CategoryCard(
    category: Category,
    isSelected: Boolean = false,
    onCategoryClick: (Category) -> Unit
) {
    Button(
        onClick = { onCategoryClick(category) },
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
            contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = if (isSelected) 4.dp else 0.dp),
        modifier = Modifier.padding(end = 8.dp)
    ) {
        Text(
            text = "${category.icon} ${category.name}",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
        )
    }
}
