package com.example.lavavanzado4.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lavavanzado4.data.Product
import com.example.lavavanzado4.ui.components.ProductCard
import com.example.lavavanzado4.ui.state.ProductUiState
import com.example.lavavanzado4.ui.viewmodel.StoreViewModel

@Composable
fun FavoritesScreen(
    viewModel: StoreViewModel,
    onProductClick: (Product) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val favoriteIds = viewModel.favoriteIds

    val favoriteProducts = if (uiState is ProductUiState.Success) {
        (uiState as ProductUiState.Success).products.filter { it.id in favoriteIds }
    } else emptyList()

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Mis Favoritos",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(16.dp)
        )

        if (favoriteProducts.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "❤️", fontSize = 64.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Aún no tenés favoritos",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Tocá el corazón en cualquier producto",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            Text(
                text = "${favoriteProducts.size} productos guardados",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(favoriteProducts, key = { it.id }) { product ->
                    ProductCard(
                        product = product,
                        isFavorite = favoriteIds.contains(product.id),
                        onProductClick = onProductClick,
                        onFavoriteToggle = { viewModel.toggleFavorite(it.id) },
                        onAddToCart = { viewModel.addToCart(it) }
                    )
                }
            }
        }
    }
}
