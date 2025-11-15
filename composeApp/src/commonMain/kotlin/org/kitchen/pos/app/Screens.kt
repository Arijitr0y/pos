package org.kitchen.pos.app

import cafe.adriel.voyager.core.screen.Screen
import androidx.compose.runtime.Composable
import com.example.pos.ui.DashboardScreen
import org.kitchen.pos.features.inventory.InventoryScreen
import org.kitchen.pos.features.myshop.MyShopScreen
import org.kitchen.pos.features.accounts.AccountsScreen
import org.kitchen.pos.features.orders.PastOrdersScreen
import org.kitchen.pos.features.settings.SettingsScreen

object Dashboard : Screen {
    @Composable override fun Content() { DashboardScreen() }
}
object Orders : Screen {
    @Composable override fun Content() { PastOrdersScreen() }
}
object Inventory : Screen {
    @Composable override fun Content() { InventoryScreen() }
}
object MyShop : Screen {
    @Composable override fun Content() { MyShopScreen() }
}
object Accounts : Screen {
    @Composable override fun Content() { AccountsScreen() }
}
object Settings : Screen {
    @Composable override fun Content() { SettingsScreen() }
}
