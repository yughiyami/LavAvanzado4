package com.example.lavavanzado4.data.repository

import com.example.lavavanzado4.data.Category
import com.example.lavavanzado4.data.MockData
import com.example.lavavanzado4.data.Product
import com.example.lavavanzado4.domain.repository.ProductRepository

class MockProductRepository : ProductRepository {

    private val _products = MockData.products.toMutableList()
    private val _categories = MockData.categories.toMutableList()

    override fun getProducts(): List<Product> = _products.toList()

    override fun getCategories(): List<Category> = _categories.toList()

    override fun getProductById(id: Int): Product? = _products.find { it.id == id }

    override fun addProduct(product: Product) {
        val newId = (_products.maxOfOrNull { it.id } ?: 0) + 1
        _products.add(product.copy(id = newId))
    }

    override fun updateProduct(product: Product) {
        val index = _products.indexOfFirst { it.id == product.id }
        if (index != -1) _products[index] = product
    }

    override fun deleteProduct(productId: Int) {
        _products.removeAll { it.id == productId }
    }

    override fun addCategory(category: Category) {
        val newId = (_categories.maxOfOrNull { it.id } ?: 0) + 1
        _categories.add(category.copy(id = newId))
    }

    override fun deleteCategory(categoryId: Int) {
        _categories.removeAll { it.id == categoryId }
    }
}
