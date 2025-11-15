package org.kitchen.pos.app

import org.koin.core.module.Module
import org.koin.dsl.module

// feature DI modules
import org.kitchen.pos.features.dashboard.dashboardModule
import org.kitchen.pos.features.orders.ordersModule
import org.kitchen.pos.features.inventory.inventoryModule
import org.kitchen.pos.features.myshop.myShopModule
import org.kitchen.pos.features.accounts.accountsModule
import org.kitchen.pos.features.settings.settingsModule

// app-wide singletons can go here later (Supabase, Http client, etc.)
val appModule: Module = module { }

val appModules = listOf(
    appModule,
    dashboardModule,
    ordersModule,
    inventoryModule,
    myShopModule,
    accountsModule,
    settingsModule,
)
