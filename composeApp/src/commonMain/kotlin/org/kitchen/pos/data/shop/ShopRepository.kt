package org.kitchen.pos.data.shop

import kotlinx.coroutines.flow.Flow

/**
 * Centralized shop repository contract.
 * Implementation will use encrypted Room (Android) + later Supabase sync.
 */
interface ShopRepository {

    /** Always emits the current shop profile (or null if not created yet). */
    val shopFlow: Flow<Shop?>

    /** Aggregated, live stats for the My Shop dashboard. */
    val statsFlow: Flow<ShopStats>

    /**
     * Creates or updates the single Shop row.
     * Implementations must guarantee there is at most one shop record.
     */
    suspend fun upsertShop(shop: Shop)

    /**
     * Returns current shop if exists, otherwise creates a sane default
     * (used on first app launch).
     */
    suspend fun getOrCreateDefaultShop(): Shop
}
