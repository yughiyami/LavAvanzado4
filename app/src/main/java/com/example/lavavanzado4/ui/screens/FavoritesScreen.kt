package com.example.lavavanzado4.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.lavavanzado4.data.Product
import com.example.lavavanzado4.ui.components.ProductCard

@Composable
fun FavoritesScreen(
    favoriteProducts: List<Product>,
    onProductClick: (Product) -> Unit,
    onFavoriteToggle: (Product) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Mis Favoritos",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )

        if (favoriteProducts.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "No tienes productos favoritos aún.")
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(favoriteProducts) { product ->
                    ProductCard(
                        product = product,
                        onProductClick = onProductClick,
                        onFavoriteToggle = onFavoriteToggle
                    )
                }
            }
        }
    }
}
