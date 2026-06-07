package com.example.lavavanzado4.domain.repository

import com.example.lavavanzado4.data.Category
import com.example.lavavanzado4.data.Product

interface ProductRepository {
    fun getProducts(): List<Product>
    fun getCategories(): List<Category>
    fun getProductById(id: Int): Product?
    fun addProduct(product: Product)
    fun updateProduct(product: Product)
    fun deleteProduct(productId: Int)
    fun addCategory(category: Category)
    fun deleteCategory(categoryId: Int)
}
