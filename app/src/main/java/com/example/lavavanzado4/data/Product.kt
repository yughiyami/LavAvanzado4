package com.example.lavavanzado4.data

data class Product(
    val id: Int,
    val name: String,
    val categoryId: Int,
    val description: String,
    val price: Double,
    val imageUrl: String,
    var isFavorite: Boolean = false
)

data class Category(
    val id: Int,
    val name: String,
    val icon: String
)

object MockData {
    val categories = listOf(
        Category(1, "Electrónica", "🔌"),
        Category(2, "Ropa", "👕"),
        Category(3, "Hogar", "🏠"),
        Category(4, "Deportes", "⚽")
    )

    val products = listOf(
        Product(1, "Smartphone Pro", 1, "Último modelo con cámara de 108MP y pantalla OLED.", 999.99, "https://via.placeholder.com/300"),
        Product(2, "Laptop Ultra", 1, "Potente y ligera para profesionales con procesador de última generación.", 1499.00, "https://via.placeholder.com/300"),
        Product(3, "Camiseta Algodón", 2, "100% algodón, disponible en varios colores y tallas.", 19.99, "https://via.placeholder.com/300"),
        Product(4, "Jeans Slim Fit", 2, "Cómodos, duraderos y con estilo moderno.", 49.99, "https://via.placeholder.com/300"),
        Product(5, "Lámpara LED", 3, "Iluminación ajustable para tu escritorio, bajo consumo.", 29.99, "https://via.placeholder.com/300"),
        Product(6, "Sofa Moderno", 3, "Tres plazas, muy confortable con tapicería premium.", 599.00, "https://via.placeholder.com/300"),
        Product(7, "Balón de Fútbol", 4, "Tamaño oficial, alta resistencia para todo tipo de terrenos.", 25.00, "https://via.placeholder.com/300"),
        Product(8, "Zapatillas Running", 4, "Máxima amortiguación y soporte para corredores de larga distancia.", 89.00, "https://via.placeholder.com/300")
    )
}
