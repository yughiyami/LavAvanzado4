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
        Product(
            id = 1,
            name = "Smartphone Pro",
            categoryId = 1,
            description = "Último modelo con cámara de 108MP y pantalla OLED de 6.7 pulgadas. Batería de 5000mAh y carga rápida de 65W.",
            price = 999.99,
            imageUrl = "https://picsum.photos/seed/smartphone-pro/400/400"
        ),
        Product(
            id = 2,
            name = "Laptop Ultra",
            categoryId = 1,
            description = "Potente y ligera para profesionales. Procesador de última generación, 16GB RAM, SSD 512GB y pantalla 4K.",
            price = 1499.00,
            imageUrl = "https://picsum.photos/seed/laptop-ultra/400/400"
        ),
        Product(
            id = 3,
            name = "Auriculares Bluetooth",
            categoryId = 1,
            description = "Cancelación activa de ruido, 30 horas de batería, calidad de sonido Hi-Fi con drivers de 40mm.",
            price = 149.99,
            imageUrl = "https://picsum.photos/seed/headphones-bt/400/400"
        ),
        Product(
            id = 4,
            name = "Camiseta Algodón",
            categoryId = 2,
            description = "100% algodón orgánico, disponible en 12 colores y tallas XS a XXL. Corte regular, lavable a máquina.",
            price = 19.99,
            imageUrl = "https://picsum.photos/seed/cotton-shirt/400/400"
        ),
        Product(
            id = 5,
            name = "Jeans Slim Fit",
            categoryId = 2,
            description = "Tela denim premium con elastan para mayor comodidad. Corte slim, disponible en azul y negro.",
            price = 49.99,
            imageUrl = "https://picsum.photos/seed/slim-jeans/400/400"
        ),
        Product(
            id = 6,
            name = "Lámpara LED",
            categoryId = 3,
            description = "Iluminación ajustable en temperatura e intensidad. Control táctil, 3 modos de luz y bajo consumo energético.",
            price = 29.99,
            imageUrl = "https://picsum.photos/seed/led-lamp/400/400"
        ),
        Product(
            id = 7,
            name = "Sofa Moderno",
            categoryId = 3,
            description = "Tres plazas, tapicería premium antidesgaste, estructura de madera maciza. Patas metálicas nórdicas.",
            price = 599.00,
            imageUrl = "https://picsum.photos/seed/modern-sofa/400/400"
        ),
        Product(
            id = 8,
            name = "Balón de Fútbol",
            categoryId = 4,
            description = "Tamaño oficial FIFA, cuero sintético de alta resistencia. Apto para todo tipo de superficies.",
            price = 25.00,
            imageUrl = "https://picsum.photos/seed/football-ball/400/400"
        ),
        Product(
            id = 9,
            name = "Zapatillas Running",
            categoryId = 4,
            description = "Máxima amortiguación y soporte para corredores de larga distancia. Suela de goma antideslizante.",
            price = 89.00,
            imageUrl = "https://picsum.photos/seed/running-shoes/400/400"
        )
    )
}
