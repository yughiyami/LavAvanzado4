package com.example.lavavanzado4.ui.state

import com.example.lavavanzado4.data.Category
import com.example.lavavanzado4.data.Product

sealed class ProductUiState {
    object Loading : ProductUiState()
    data class Success(
        val products: List<Product>,
        val categories: List<Category>
    ) : ProductUiState()
    data class Error(val message: String) : ProductUiState()
}
