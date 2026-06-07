package com.example.lavavanzado4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
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
import com.example.lavavanzado4.data.repository.MockProductRepository
import com.example.lavavanzado4.ui.screens.*
import com.example.lavavanzado4.ui.theme.LavAvanzado4Theme
import com.example.lavavanzado4.ui.viewmodel.StoreViewModel
import com.example.lavavanzado4.ui.viewmodel.StoreViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
    object Categories : Screen("categories", "Categorías", Icons.Default.Category)
    object Details : Screen("details/{productId}", "Detalles", Icons.Default.Home)
    object AddProduct : Screen("add_product", "Agregar", Icons.Default.Home)
    object EditProduct : Screen("edit_product/{productId}", "Editar", Icons.Default.Home)
    object AddCategory : Screen("add_category", "Nueva Categoría", Icons.Default.Category)
}

// Rutas que muestran la barra de navegación inferior
private val bottomBarRoutes = setOf("home", "favorites", "cart", "categories")

@Composable
fun MainContent(viewModel: StoreViewModel) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = bottomBarRoutes.any { currentRoute?.startsWith(it) == true }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    val navItems = listOf(Screen.Home, Screen.Favorites, Screen.Cart, Screen.Categories)
                    navItems.forEach { screen ->
                        NavigationBarItem(
                            icon = { Icon(screen.icon, contentDescription = screen.title) },
                            label = { Text(screen.title) },
                            selected = currentRoute?.startsWith(screen.route.split("/")[0]) == true,
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
                    },
                    onAddProduct = {
                        navController.navigate(Screen.AddProduct.route)
                    },
                    onManageCategories = {
                        navController.navigate(Screen.Categories.route)
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

            composable(Screen.Categories.route) {
                CategoriesScreen(
                    viewModel = viewModel,
                    onBackClick = { navController.popBackStack() },
                    onAddCategory = { navController.navigate(Screen.AddCategory.route) }
                )
            }

            composable(
                route = Screen.Details.route,
                arguments = listOf(navArgument("productId") { type = NavType.IntType })
            ) { backStackEntry ->
                val productId = backStackEntry.arguments?.getInt("productId") ?: return@composable
                DetailsScreen(
                    productId = productId,
                    viewModel = viewModel,
                    onBackClick = { navController.popBackStack() },
                    onAddToCart = { product ->
                        viewModel.addToCart(product)
                        navController.navigate(Screen.Cart.route)
                    },
                    onEditClick = { product ->
                        navController.navigate("edit_product/${product.id}")
                    }
                )
            }

            composable(Screen.AddProduct.route) {
                AddProductScreen(
                    viewModel = viewModel,
                    onBackClick = { navController.popBackStack() },
                    onProductAdded = { navController.popBackStack() }
                )
            }

            composable(
                route = Screen.EditProduct.route,
                arguments = listOf(navArgument("productId") { type = NavType.IntType })
            ) { backStackEntry ->
                val productId = backStackEntry.arguments?.getInt("productId") ?: return@composable
                EditProductScreen(
                    productId = productId,
                    viewModel = viewModel,
                    onBackClick = { navController.popBackStack() },
                    onProductUpdated = { navController.popBackStack() },
                    onProductDeleted = {
                        navController.popBackStack(Screen.Home.route, inclusive = false)
                    }
                )
            }

            composable(Screen.AddCategory.route) {
                AddCategoryScreen(
                    viewModel = viewModel,
                    onBackClick = { navController.popBackStack() },
                    onCategoryAdded = { navController.popBackStack() }
                )
            }
        }
    }
}
