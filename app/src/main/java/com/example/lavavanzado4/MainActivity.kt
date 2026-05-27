package com.example.lavavanzado4

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.lavavanzado4.data.MockData
import com.example.lavavanzado4.data.repository.MockProductRepository
import com.example.lavavanzado4.ui.screens.*
import com.example.lavavanzado4.ui.theme.LavAvanzado4Theme
import com.example.lavavanzado4.ui.viewmodel.StoreViewModel
import com.example.lavavanzado4.ui.viewmodel.StoreViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set window background to help with initial render
        window.setBackgroundDrawable(android.graphics.drawable.ColorDrawable(android.graphics.Color.WHITE))
        enableEdgeToEdge()
        setContent {
            val repository = remember { MockProductRepository() }
            val viewModel: StoreViewModel = viewModel(factory = StoreViewModelFactory(repository))
            
            val currentTheme by viewModel.currentTheme
            
            LavAvanzado4Theme(themeMode = currentTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainContent(viewModel = viewModel)
                }
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
fun MainContent(viewModel: StoreViewModel) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

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
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Palette, contentDescription = "Tema") },
                    label = { Text("Tema") },
                    selected = false,
                    onClick = { viewModel.toggleTheme() }
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
                    viewModel = viewModel,
                    onProductClick = { product ->
                        navController.navigate("details/${product.id}")
                    }
                )
            }
            composable(Screen.Favorites.route) {
                FavoritesScreen(
                    viewModel = viewModel,
                    onProductClick = { product ->
                        navController.navigate("details/${product.id}")
                    }
                )
            }
            composable(Screen.Cart.route) {
                CartScreen(viewModel = viewModel)
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
                            viewModel.addToCart(p)
                            navController.navigate(Screen.Cart.route)
                        }
                    )
                }
            }
        }
    }
}
