package org.kitchen.pos.data

import org.kitchen.pos.data.shop.InMemoryShopRepository
import org.kitchen.pos.data.shop.ShopRepository

object AppRepositories {

    private var _shopRepository: ShopRepository? = null

    val shopRepository: ShopRepository
        get() = _shopRepository ?: InMemoryShopRepository().also {
            _shopRepository = it
        }

    fun init(shopRepository: ShopRepository) {
        _shopRepository = shopRepository
    }
}
