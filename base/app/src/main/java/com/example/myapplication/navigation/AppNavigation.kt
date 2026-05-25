package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.model.Category
import com.example.myapplication.model.Product
import com.example.myapplication.ui.screens.DetailScreen
import com.example.myapplication.ui.screens.HomeScreen
import com.example.myapplication.R
@Composable
fun AppNavigation(
    onThemeChange: (String) -> Unit
) {
    //datos
    val gamingCategory = Category(
        id = 1,
        name = "Gaming"
    )

    val accessoriesCategory = Category(
        id = 2,
        name = "Accesorios"
    )
    var productsState by remember {
        mutableStateOf(
            listOf(
                Product(
                    id = 1,
                    name = "Laptop Gamer",
                    description = "RTX 4070 + Ryzen 9",
                    price = 2500.0,
                    category = gamingCategory,
                    imageRes = android.R.drawable.ic_menu_gallery
                ),
                Product(
                    id = 2,
                    name = "Mechanical Keyboard",
                    description = "RGB Switch Blue",
                    price = 120.0,
                    category = accessoriesCategory,
                    imageRes = android.R.drawable.ic_menu_gallery
                ),
                Product(
                    id = 3,
                    name = "Gaming Mouse",
                    description = "16000 DPI",
                    price = 75.0,
                    category = accessoriesCategory,
                    imageRes = android.R.drawable.ic_menu_gallery
                )
            )
        )
    }
    val categories = remember {
        listOf(
            gamingCategory,
            accessoriesCategory
        )
    }
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                navController,
                onThemeChange,
                productsState,
                categories,
                onToggleFavorite = { product ->
                    productsState = productsState.map {
                        if (it.id == product.id)
                            it.copy(isFavorite = !it.isFavorite)
                        else it
                    }
                }
            )
        }
        composable(
            "detail/{name}/{price}"
        ) { backStack ->
            val name =
                backStack.arguments?.getString("name") ?: ""
            val price =
                backStack.arguments?.getString("price") ?: ""
            DetailScreen(
                name,
                price
            )
        }
    }
}