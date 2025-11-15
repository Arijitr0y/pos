package org.kitchen.pos.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer

private data class DrawerItem(
    val label: String,
    val screen: Screen
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val items = listOf(
        DrawerItem("Dashboard", Dashboard),
        DrawerItem("Orders",    Orders),
        DrawerItem("Inventory", Inventory),
        DrawerItem("My Shop",   MyShop),
        DrawerItem("Accounts",  Accounts),
        DrawerItem("Settings",  Settings)
    )

    Navigator(items.first().screen) { navigator ->
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    items.forEach { item ->
                        NavigationDrawerItem(
                            selected = item.screen::class == navigator.lastItem::class,
                            onClick = {
                                navigator.replaceAll(item.screen)
                                scope.launch { drawerState.close() }
                            },
                            label = { Text(item.label) }
                        )
                    }
                }
            }
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(items.firstOrNull { it.screen::class == navigator.lastItem::class }?.label ?: "POS") },
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(Icons.Default.Menu, contentDescription = "Menu")
                            }
                        }
                    )
                }
            ) { inner ->
                Box(Modifier.padding(inner)) {
                    CurrentScreen() // renders the active Voyager Screen
                }
            }
        }
    }
}
