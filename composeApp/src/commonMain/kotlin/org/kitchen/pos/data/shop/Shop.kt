package org.kitchen.pos.data.shop

/**
 * Core shop profile used across the whole app.
 * This is the single source of truth for "My Shop".
 *
 * Note: we use Long millis instead of Instant to avoid experimental APIs
 * and keep it commonMain-friendly.
 */
data class Shop(
    val id: Long = 1L, // enforce single-row, avoids duplicates
    val name: String,
    val ownerName: String,
    val phone: String,
    val email: String,
    val addressLine1: String,
    val addressLine2: String,
    val city: String,
    val state: String,
    val pincode: String,
    val gstin: String?,
    val currencyCode: String = "INR",
    val taxInclusivePricing: Boolean = true,
    val allowNegativeStock: Boolean = false,

    // timestamps in epoch millis
    val createdAtMillis: Long,
    val updatedAtMillis: Long,
)

/**
 * Aggregated stats for dashboard tiles on My Shop page.
 * These are read-only views derived from local DB tables.
 */
data class ShopStats(
    val totalProducts: Int = 0,
    val lowStockItems: Int = 0,
    val todaySalesAmount: Double = 0.0,
    val pendingDuesAmount: Double = 0.0,
)
