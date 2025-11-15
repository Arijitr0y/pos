package org.kitchen.pos.features.orders

interface OrdersRepository {
    suspend fun loadOrders(): List<String>
}

// Simple fake impl for now
class OrdersRepositoryFake : OrdersRepository {
    override suspend fun loadOrders(): List<String> =
        listOf("Order #1001", "Order #1002", "Order #1003")
}
