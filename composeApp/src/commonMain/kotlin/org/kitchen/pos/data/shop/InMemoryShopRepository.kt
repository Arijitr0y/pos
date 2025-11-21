package org.kitchen.pos.data.shop

import io.ktor.util.date.getTimeMillis
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


/**
 * Temporary implementation so the UI works everywhere (Android/Desktop/Web).
 * For real offline persistence, Android will use encrypted Room, but
 * the interface remains the same.
 */
class InMemoryShopRepository : ShopRepository {

    private val _shopFlow = MutableStateFlow<Shop?>(null)
    override val shopFlow = _shopFlow.asStateFlow()

    private val _statsFlow = MutableStateFlow(ShopStats())
    override val statsFlow = _statsFlow.asStateFlow()

    override suspend fun upsertShop(shop: Shop) {
        val now = getTimeMillis()
        val normalized = shop.copy(
            id = 1L, // enforce single record
            updatedAtMillis = now,
            createdAtMillis = if (shop.createdAtMillis != 0L) shop.createdAtMillis else now
        )
        _shopFlow.value = normalized
    }

    override suspend fun getOrCreateDefaultShop(): Shop {
        val existing = _shopFlow.value
        if (existing != null) return existing

        val now = getTimeMillis()
        val default = Shop(
            id = 1L,
            name = "My Shop",
            ownerName = "",
            phone = "",
            email = "",
            addressLine1 = "",
            addressLine2 = "",
            city = "",
            state = "",
            pincode = "",
            gstin = null,
            currencyCode = "INR",
            taxInclusivePricing = true,
            allowNegativeStock = false,
            createdAtMillis = now,
            updatedAtMillis = now
        )
        _shopFlow.value = default
        return default
    }

    // Optional: simple helper for demo/testing
    fun updateStats(transform: (ShopStats) -> ShopStats) {
        _statsFlow.value = transform(_statsFlow.value)
    }
}
