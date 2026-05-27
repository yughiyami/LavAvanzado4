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
import androidx.compose.ui.unit.sp
import com.example.lavavanzado4.data.Product
import com.example.lavavanzado4.ui.components.ProductCard
import com.example.lavavanzado4.ui.viewmodel.StoreViewModel

@Composable
fun FavoritesScreen(
    viewModel: StoreViewModel,
    onProductClick: (Product) -> Unit
) {
    val favoriteProducts = viewModel.favoriteProducts

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
                        text = "Aún no tienes favoritos",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(favoriteProducts) { product ->
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
}
