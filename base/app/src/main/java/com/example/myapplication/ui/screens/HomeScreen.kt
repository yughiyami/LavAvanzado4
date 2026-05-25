package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.model.Category
import com.example.myapplication.model.Product
import com.example.myapplication.ui.components.AppToolbar
import com.example.myapplication.ui.components.CategorySelector
import com.example.myapplication.ui.components.ProductCard
import com.example.myapplication.ui.components.ProductSearchBar
import com.example.myapplication.ui.components.ThemeSelector

@Composable
fun HomeScreen(
    navController: NavController,
    onThemeChange: (String) -> Unit,
    products: List<Product>,
    categories: List<Category>,
    onToggleFavorite: (Product) -> Unit
) {
    var filteredProducts by remember {
        mutableStateOf(products)
    }
    var searchText by remember {
        mutableStateOf("")
    }
    var selectedCategory by remember {
        mutableStateOf<Category?>(null)
    }
    fun applyFilters() {
        filteredProducts = products.filter { product ->
            val matchesSearch =
                product.name.contains(
                    searchText,
                    ignoreCase = true
                )
            val matchesCategory =
                selectedCategory == null ||
                        product.category.id == selectedCategory?.id

            matchesSearch && matchesCategory
        }
    }
    Column {
        AppToolbar(
            title = "Modular Store"
        )
        Spacer(modifier = Modifier.height(12.dp))
        ThemeSelector {
            onThemeChange(it.name)
        }
        // buscador
        Spacer(modifier = Modifier.height(12.dp))
        ProductSearchBar(
            searchText = searchText,
            onSearchTextChange = { text ->
                searchText = text
                applyFilters()
            }
        )
        // categorias
        Spacer(modifier = Modifier.height(12.dp))
        CategorySelector(
            categories = categories,
            selectedCategory = selectedCategory
        ) { category ->
            selectedCategory = category
            applyFilters()
        }
        LazyColumn {
            items(filteredProducts) { product ->
                ProductCard(
                    product = product,
                    onToggleFavorite = {
                        onToggleFavorite(it)
                        applyFilters()
                    },
                ) {
                    navController.navigate(
                        "detail/${it.name}/${it.price}"
                    )
                }
            }
        }
    }
}
