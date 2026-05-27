package com.example.lavavanzado4.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ProductSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        placeholder = { Text("Buscar productos...", color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)) },
        leadingIcon = { 
            Icon(
                Icons.Default.Search, 
                contentDescription = "Buscar",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            ) 
        },
        trailingIcon = {
            IconButton(onClick = { /* Open Filters */ }) {
                Icon(
                    Icons.Default.FilterList, 
                    contentDescription = "Filtros",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
            focusedBorderColor = MaterialTheme.colorScheme.primary
        )
    )
}
