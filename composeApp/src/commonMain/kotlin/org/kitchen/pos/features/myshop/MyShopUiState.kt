package org.kitchen.pos.features.myshop

import org.kitchen.pos.data.shop.Shop
import org.kitchen.pos.data.shop.ShopStats

data class MyShopUiState(
    val isLoading: Boolean = true,
    val shop: Shop? = null,
    val stats: ShopStats = ShopStats(),
    val isEditing: Boolean = false,
    val errorMessage: String? = null,
    val isSaving: Boolean = false,
)
