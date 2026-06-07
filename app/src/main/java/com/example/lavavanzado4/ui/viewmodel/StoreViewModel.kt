package com.example.lavavanzado4.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.lavavanzado4.data.Category
import com.example.lavavanzado4.data.Product
import com.example.lavavanzado4.domain.repository.ProductRepository
import com.example.lavavanzado4.ui.state.ProductUiState
import com.example.lavavanzado4.ui.theme.AppThemeMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class StoreViewModel(private val repository: ProductRepository) : ViewModel() {

    // UI State Pattern — sealed class exposes Loading / Success / Error
    private val _uiState = MutableStateFlow<ProductUiState>(ProductUiState.Loading)
    val uiState: StateFlow<ProductUiState> = _uiState.asStateFlow()

    // Search & filter — StateFlow para observación reactiva
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategoryId = MutableStateFlow<Int?>(null)
    val selectedCategoryId: StateFlow<Int?> = _selectedCategoryId.asStateFlow()

    // Favoritos y carrito — Compose state para recomposición inmediata
    val favoriteIds = mutableStateListOf<Int>()
    val cartItems = mutableStateListOf<Product>()

    var currentTheme = mutableStateOf(AppThemeMode.MODERN_BLUE)
        private set

    init {
        loadData()
    }

    // ── Carga de datos ────────────────────────────────────────────────────────

    fun loadData() {
        _uiState.value = ProductUiState.Loading
        try {
            val products = repository.getProducts()
            val categories = repository.getCategories()
            _uiState.value = ProductUiState.Success(products, categories)
        } catch (e: Exception) {
            _uiState.value = ProductUiState.Error(e.message ?: "Error desconocido al cargar datos")
        }
    }

    // ── CRUD Productos ────────────────────────────────────────────────────────

    fun addProduct(product: Product) {
        repository.addProduct(product)
        loadData()
    }

    fun updateProduct(product: Product) {
        repository.updateProduct(product)
        loadData()
    }

    fun deleteProduct(productId: Int) {
        repository.deleteProduct(productId)
        favoriteIds.remove(productId)
        cartItems.removeAll { it.id == productId }
        loadData()
    }

    fun getProductById(id: Int): Product? {
        val state = _uiState.value
        return if (state is ProductUiState.Success) state.products.find { it.id == id } else null
    }

    // ── CRUD Categorías ───────────────────────────────────────────────────────

    fun addCategory(category: Category) {
        repository.addCategory(category)
        loadData()
    }

    fun deleteCategory(categoryId: Int) {
        repository.deleteCategory(categoryId)
        loadData()
    }

    // ── Filtros ───────────────────────────────────────────────────────────────

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun selectCategory(categoryId: Int?) {
        _selectedCategoryId.value = categoryId
    }

    fun getFilteredProducts(): List<Product> {
        val state = _uiState.value
        if (state !is ProductUiState.Success) return emptyList()
        val query = _searchQuery.value
        val catId = _selectedCategoryId.value
        return state.products.filter { product ->
            val matchesSearch = product.name.contains(query, ignoreCase = true) ||
                product.description.contains(query, ignoreCase = true)
            val matchesCategory = catId == null || product.categoryId == catId
            matchesSearch && matchesCategory
        }
    }

    // ── Favoritos ─────────────────────────────────────────────────────────────

    fun toggleFavorite(productId: Int) {
        if (productId in favoriteIds) {
            favoriteIds.remove(productId)
        } else {
            favoriteIds.add(productId)
        }
    }

    fun getFavoriteProducts(): List<Product> {
        val state = _uiState.value
        return if (state is ProductUiState.Success) {
            state.products.filter { it.id in favoriteIds }
        } else emptyList()
    }

    // ── Carrito ───────────────────────────────────────────────────────────────

    fun addToCart(product: Product) {
        if (!cartItems.any { it.id == product.id }) {
            cartItems.add(product)
        }
    }

    fun removeFromCart(product: Product) {
        cartItems.remove(product)
    }

    // ── Tema ──────────────────────────────────────────────────────────────────

    fun toggleTheme() {
        currentTheme.value = when (currentTheme.value) {
            AppThemeMode.MODERN_BLUE -> AppThemeMode.GREEN
            AppThemeMode.GREEN -> AppThemeMode.PURPLE
            AppThemeMode.PURPLE -> AppThemeMode.ORANGE
            AppThemeMode.ORANGE -> AppThemeMode.MODERN_BLUE
        }
    }
}
