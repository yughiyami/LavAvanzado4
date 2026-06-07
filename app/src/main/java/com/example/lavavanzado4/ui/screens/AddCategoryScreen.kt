package com.example.lavavanzado4.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.lavavanzado4.domain.model.Category
import com.example.lavavanzado4.ui.viewmodel.StoreViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AddCategoryScreen(
    viewModel: StoreViewModel,
    onBackClick: () -> Unit,
    onCategoryAdded: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var icon by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf(false) }
    var iconError by remember { mutableStateOf(false) }

    val suggestedIcons = listOf("📦", "🎮", "🎵", "📚", "🍔", "🚗", "💄", "🐾", "🌿", "🏋️")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nueva Categoría", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Información de la Categoría",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            OutlinedTextField(
                value = name,
                onValueChange = { name = it; nameError = false },
                label = { Text("Nombre de la categoría *") },
                isError = nameError,
                supportingText = { if (nameError) Text("Campo requerido") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = icon,
                onValueChange = { icon = it; iconError = false },
                label = { Text("Emoji / Ícono *") },
                isError = iconError,
                supportingText = { if (iconError) Text("Ingresá un emoji") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                placeholder = { Text("Ej: 🎮") }
            )

            Text(
                text = "Sugerencias:",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                suggestedIcons.forEach { emoji ->
                    FilterChip(
                        selected = icon == emoji,
                        onClick = { icon = emoji; iconError = false },
                        label = { Text(emoji, style = MaterialTheme.typography.titleLarge) }
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    nameError = name.isBlank()
                    iconError = icon.isBlank()
                    if (!nameError && !iconError) {
                        viewModel.addCategory(
                            Category(id = 0, name = name.trim(), icon = icon.trim())
                        )
                        onCategoryAdded()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text("Guardar Categoría", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            }
        }
    }
}
