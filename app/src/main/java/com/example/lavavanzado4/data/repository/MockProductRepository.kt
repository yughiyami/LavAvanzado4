package com.example.lavavanzado4.data.repository

import com.example.lavavanzado4.data.Category
import com.example.lavavanzado4.data.MockData
import com.example.lavavanzado4.data.Product
import com.example.lavavanzado4.domain.repository.ProductRepository

/**
 * SOLID: Liskov Substitution.
 * This class implements the ProductRepository interface using mock data.
 * It can be easily replaced by a RemoteRepository without changing the business logic.
 */
class MockProductRepository : ProductRepository {
    override fun getProducts(): List<Product> = MockData.products
    override fun getCategories(): List<Category> = MockData.categories
}
