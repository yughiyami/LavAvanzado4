package com.example.lavavanzado4.domain.repository

import com.example.lavavanzado4.data.Category
import com.example.lavavanzado4.data.Product
import kotlinx.coroutines.flow.Flow

/**
 * SOLID: Interface Segregation & Dependency Inversion.
 * This interface defines the contract for data operations, decoupling the UI from the data source.
 */
interface ProductRepository {
    fun getProducts(): List<Product>
    fun getCategories(): List<Category>
}
