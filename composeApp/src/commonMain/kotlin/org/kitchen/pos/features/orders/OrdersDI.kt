package org.kitchen.pos.features.orders

import org.koin.dsl.module

val ordersModule = module {
    single<OrdersRepository> { OrdersRepositoryFake() }
    factory { OrdersViewModel(get()) }
}
