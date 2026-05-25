package com.example.myapplication.model

data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val category: Category,
    val imageRes: Int,
    val isFavorite: Boolean = false
)