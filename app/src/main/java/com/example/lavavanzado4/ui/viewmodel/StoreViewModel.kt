package com.example.lavavanzado4.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.lavavanzado4.data.Category
import com.example.lavavanzado4.data.Product
import com.example.lavavanzado4.domain.repository.ProductRepository
import com.example.lavavanzado4.ui.theme.AppThemeMode

/**
 * SOLID: Single Responsibility Principle.
 * El ViewModel centraliza el estado de la UI y la lógica de negocio.
 */
class StoreViewModel(private val repository: ProductRepository) : ViewModel() {

    // UI State - Modern Blue as default
    var currentTheme = mutableStateOf(AppThemeMode.MODERN_BLUE)
        private set

    var searchQuery = mutableStateOf("")
        private set

    var selectedCategoryId = mutableStateOf<Int?>(null)
        private set

    val favoriteProducts = mutableStateListOf<Product>()
    val cartItems = mutableStateListOf<Product>()

    // Data from Repository
    val products: List<Product> = repository.getProducts()
    val categories: List<Category> = repository.getCategories()

    // Logic
    fun updateSearchQuery(query: String) {
        searchQuery.value = query
    }

    fun selectCategory(categoryId: Int?) {
        selectedCategoryId.value = categoryId
    }

    fun toggleTheme() {
        currentTheme.value = when (currentTheme.value) {
            AppThemeMode.MODERN_BLUE -> AppThemeMode.GREEN
            AppThemeMode.GREEN -> AppThemeMode.PURPLE
            AppThemeMode.PURPLE -> AppThemeMode.ORANGE
            AppThemeMode.ORANGE -> AppThemeMode.MODERN_BLUE
        }
    }

    fun toggleFavorite(product: Product) {
        // Find product in source list to update state if using shared objects
        val sourceProduct = products.find { it.id == product.id } ?: product
        sourceProduct.isFavorite = !sourceProduct.isFavorite
        
        if (sourceProduct.isFavorite) {
            if (!favoriteProducts.any { it.id == sourceProduct.id }) favoriteProducts.add(sourceProduct)
        } else {
            favoriteProducts.removeAll { it.id == sourceProduct.id }
        }
    }

    fun addToCart(product: Product) {
        if (!cartItems.any { it.id == product.id }) {
            cartItems.add(product)
        }
    }

    fun removeFromCart(product: Product) {
        cartItems.remove(product)
    }

    fun getFilteredProducts(): List<Product> {
        return products.filter { product ->
            val matchesSearch = product.name.contains(searchQuery.value, ignoreCase = true)
            val matchesCategory = (selectedCategoryId.value == null || product.categoryId == selectedCategoryId.value)
            matchesSearch && matchesCategory
        }
    }
}
