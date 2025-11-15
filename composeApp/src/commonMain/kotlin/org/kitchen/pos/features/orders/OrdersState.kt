package org.kitchen.pos.features.orders

data class OrdersState(
    val isLoading: Boolean = false,
    val items: List<String> = emptyList(),
    val error: String? = null
)
