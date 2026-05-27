package com.example.lavavanzado4.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.lavavanzado4.data.Category
import com.example.lavavanzado4.data.Product
import com.example.lavavanzado4.ui.components.CategoryCard
import com.example.lavavanzado4.ui.components.ProductCard
import com.example.lavavanzado4.ui.components.ProductSearchBar
import com.example.lavavanzado4.ui.viewmodel.StoreViewModel

/**
 * SOLID: Open/Closed Principle.
 * Pantalla de inicio que integra búsqueda y categorías dinámicas.
 */
@Composable
fun HomeScreen(
    viewModel: StoreViewModel,
    onProductClick: (Product) -> Unit
) {
    val filteredProducts = viewModel.getFilteredProducts()
    val searchQuery by viewModel.searchQuery
    val selectedCategoryId by viewModel.selectedCategoryId

    Column(modifier = Modifier.fillMaxSize()) {
        ProductSearchBar(
            query = searchQuery,
            onQueryChange = { viewModel.updateSearchQuery(it) }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Categorías",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            item {
                CategoryCard(
                    category = Category(0, "Todo", "🔥"),
                    isSelected = selectedCategoryId == null,
                    onCategoryClick = { viewModel.selectCategory(null) }
                )
            }
            items(viewModel.categories) { category ->
                CategoryCard(
                    category = category,
                    isSelected = selectedCategoryId == category.id,
                    onCategoryClick = { viewModel.selectCategory(it.id) }
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Productos Destacados",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            TextButton(onClick = { /* View All */ }) {
                Text(text = "Ver todo", style = MaterialTheme.typography.labelLarge)
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(filteredProducts) { product ->
                ProductCard(
                    product = product,
                    onProductClick = onProductClick,
                    onFavoriteToggle = { viewModel.toggleFavorite(it) },
                    onAddToCart = { viewModel.addToCart(it) }
                )
            }
        }
    }
}
