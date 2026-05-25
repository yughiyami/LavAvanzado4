package com.example.lavavanzado4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.lavavanzado4.data.MockData
import com.example.lavavanzado4.data.Product
import com.example.lavavanzado4.ui.screens.*
import com.example.lavavanzado4.ui.theme.AppThemeMode
import com.example.lavavanzado4.ui.theme.LavAvanzado4Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var currentTheme by remember { mutableStateOf(AppThemeMode.BLUE) }
            
            LavAvanzado4Theme(themeMode = currentTheme) {
                MainContent(
                    onThemeChange = { 
                        currentTheme = when(currentTheme) {
                            AppThemeMode.BLUE -> AppThemeMode.GREEN
                            AppThemeMode.GREEN -> AppThemeMode.PURPLE
                            AppThemeMode.PURPLE -> AppThemeMode.ORANGE
                            AppThemeMode.ORANGE -> AppThemeMode.BLUE
                        }
                    }
                )
            }
        }
    }
}

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Inicio", Icons.Default.Home)
    object Favorites : Screen("favorites", "Favoritos", Icons.Default.Favorite)
    object Cart : Screen("cart", "Carrito", Icons.Default.ShoppingCart)
    object Details : Screen("details/{productId}", "Detalles", Icons.Default.Home)
}

@Composable
fun MainContent(onThemeChange: () -> Unit) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val favoriteProducts = remember { mutableStateListOf<Product>() }
    val cartItems = remember { mutableStateListOf<Product>() }

    Scaffold(
        bottomBar = {
            NavigationBar {
                val items = listOf(Screen.Home, Screen.Favorites, Screen.Cart)
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = currentDestination?.route?.startsWith(screen.route.split("/")[0]) == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
                // Theme Toggle Item
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Palette, contentDescription = "Tema") },
                    label = { Text("Tema") },
                    selected = false,
                    onClick = onThemeChange
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    onProductClick = { product ->
                        navController.navigate("details/${product.id}")
                    },
                    onFavoriteToggle = { product ->
                        product.isFavorite = !product.isFavorite
                        if (product.isFavorite) {
                            if (!favoriteProducts.any { it.id == product.id }) favoriteProducts.add(product)
                        } else {
                            favoriteProducts.removeAll { it.id == product.id }
                        }
                    }
                )
            }
            composable(Screen.Favorites.route) {
                FavoritesScreen(
                    favoriteProducts = favoriteProducts,
                    onProductClick = { product ->
                        navController.navigate("details/${product.id}")
                    },
                    onFavoriteToggle = { product ->
                        product.isFavorite = !product.isFavorite
                        favoriteProducts.removeAll { it.id == product.id }
                    }
                )
            }
            composable(Screen.Cart.route) {
                CartScreen(
                    cartItems = cartItems,
                    onRemoveFromCart = { product ->
                        cartItems.remove(product)
                    }
                )
            }
            composable(
                route = Screen.Details.route,
                arguments = listOf(navArgument("productId") { type = NavType.IntType })
            ) { backStackEntry ->
                val productId = backStackEntry.arguments?.getInt("productId")
                val product = MockData.products.find { it.id == productId }
                product?.let {
                    DetailsScreen(
                        product = it,
                        onBackClick = { navController.popBackStack() },
                        onAddToCart = { p ->
                            if (!cartItems.any { item -> item.id == p.id }) {
                                cartItems.add(p)
                            }
                            navController.navigate(Screen.Cart.route)
                        }
                    )
                }
            }
        }
    }
}
