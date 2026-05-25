package com.example.lavavanzado4.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.lavavanzado4.data.Category
import com.example.lavavanzado4.data.MockData
import com.example.lavavanzado4.data.Product
import com.example.lavavanzado4.ui.components.CategoryCard
import com.example.lavavanzado4.ui.components.ProductCard
import com.example.lavavanzado4.ui.components.ProductSearchBar

@Composable
fun HomeScreen(
    onProductClick: (Product) -> Unit,
    onFavoriteToggle: (Product) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategoryId by remember { mutableStateOf<Int?>(null) }

    val filteredProducts = MockData.products.filter { product ->
        val matchesSearch = product.name.contains(searchQuery, ignoreCase = true)
        val matchesCategory = selectedCategoryId == null || product.categoryId == selectedCategoryId
        matchesSearch && matchesCategory
    }

    Column(modifier = Modifier.fillMaxSize()) {
        ProductSearchBar(
            query = searchQuery,
            onQueryChange = { searchQuery = it }
        )

        Text(
            text = "Categorías",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 12.dp),
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            item {
                CategoryCard(
                    category = Category(0, "Todo", "🔍"),
                    isSelected = selectedCategoryId == null,
                    onCategoryClick = { selectedCategoryId = null }
                )
            }
            items(MockData.categories) { category ->
                CategoryCard(
                    category = category,
                    isSelected = selectedCategoryId == category.id,
                    onCategoryClick = { selectedCategoryId = it.id }
                )
            }
        }

        Text(
            text = "Productos Destacados",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(filteredProducts) { product ->
                ProductCard(
                    product = product,
                    onProductClick = onProductClick,
                    onFavoriteToggle = onFavoriteToggle
                )
            }
        }
    }
}
