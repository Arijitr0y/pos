package org.kitchen.pos.features.orders.model

data class PastOrder(
    val id: String,
    val customer: String,
    val dateTime: String,
    val amount: Double,
    val status: OrderStatus
)

enum class OrderStatus { Completed, Refunded, PartialRefund, Voided }
