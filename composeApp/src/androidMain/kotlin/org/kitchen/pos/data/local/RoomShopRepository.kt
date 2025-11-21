package org.kitchen.pos.data.shop

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import org.kitchen.pos.data.local.ShopDao
import org.kitchen.pos.data.local.toDomain
import org.kitchen.pos.data.local.toEntity

/**
 * Android implementation of ShopRepository backed by Room.
 * Offline-first: reads/writes local DB only.
 */
class RoomShopRepository(
    private val dao: ShopDao
) : ShopRepository {

    override val shopFlow: Flow<Shop?> =
        dao.observeShop().map { it?.toDomain() }

    private val _statsFlow = MutableStateFlow(ShopStats())
    override val statsFlow: Flow<ShopStats> = _statsFlow

    override suspend fun upsertShop(shop: Shop) {
        val now = System.currentTimeMillis()   // ✅ no getTimeMillis
        val updated = shop.copy(
            id = 1L,
            updatedAtMillis = now,
            createdAtMillis = if (shop.createdAtMillis != 0L) shop.createdAtMillis else now
        )
        dao.upsert(updated.toEntity())
    }

    override suspend fun getOrCreateDefaultShop(): Shop {
        val existing = dao.getShopOrNull()?.toDomain()
        if (existing != null) return existing

        val now = System.currentTimeMillis()   // ✅ no getTimeMillis
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
        dao.upsert(default.toEntity())
        return default
    }

    fun updateStats(transform: (ShopStats) -> ShopStats) {
        _statsFlow.update(transform)
    }
}
