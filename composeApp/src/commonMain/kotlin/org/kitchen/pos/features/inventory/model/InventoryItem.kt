package org.kitchen.pos.features.inventory.model

data class InventoryItem(
    val id: String,
    val name: String,
    val qtyLabel: String,
    val status: StockStatus,
    val thumbnailEmoji: String,  // keep simple; replace with image later
    val badgeCount: Int = 0
)

enum class StockStatus { InStock, LowStock, OutOfStock }
