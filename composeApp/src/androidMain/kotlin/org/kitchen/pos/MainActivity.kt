package org.kitchen.pos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import org.kitchen.pos.app.AppNavigation
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.kitchen.pos.app.appModules   // <-- your Koin modules list
import org.kitchen.pos.data.local.AppDatabaseProvider
import org.kitchen.pos.data.shop.RoomShopRepository
import org.kitchen.pos.data.AppRepositories

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ✅ Centralized, offline-first Room repo for the whole app
        val db = AppDatabaseProvider.get(applicationContext)
        AppRepositories.init(
            RoomShopRepository(db.shopDao())
        )



        // Start Koin ONCE (guarded)
        if (GlobalContext.getOrNull() == null) {
            startKoin {
                modules(appModules)
            }
        }

        setContent {
            MaterialTheme { // <-- use MaterialTheme instead of AppTheme
                AppRoot()
            }
        }
    }
}

@Composable
private fun AppRoot() {
    AppNavigation() // your root composable
}
