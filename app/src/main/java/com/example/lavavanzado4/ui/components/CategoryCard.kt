package com.example.lavavanzado4.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lavavanzado4.data.Category

@Composable
fun CategoryCard(
    category: Category,
    isSelected: Boolean = false,
    onCategoryClick: (Category) -> Unit
) {
    Card(
        modifier = Modifier.padding(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        AppButton(
            text = if (isSelected) "(*) ${category.icon} ${category.name}" else "${category.icon} ${category.name}",
            modifier = Modifier.padding(2.dp),
            onClick = { onCategoryClick(category) }
        )
    }
}
