package org.kitchen.pos.features.inventory

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.kitchen.pos.features.inventory.components.InventoryRow
import org.kitchen.pos.features.inventory.components.LowStockChipCard
import org.kitchen.pos.features.inventory.components.StatusChip
import org.kitchen.pos.features.inventory.model.InventoryItem
import org.kitchen.pos.features.inventory.model.StockStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryScreen(
    onAddInventory: () -> Unit = {},
    onItemClick: (InventoryItem) -> Unit = {}
) {
    var query by remember { mutableStateOf("") }
    val items = remember { sampleInventory() }
    val lowStocks = remember { items.filter { it.status != StockStatus.InStock } }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Inventory") },
                actions = { IconButton(onClick = {}) { Icon(Icons.Default.MoreVert, null) } }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddInventory) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { inner ->
        Column(
            Modifier
                .padding(inner)
                .fillMaxSize()
        ) {
            // search
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = { Text("Search items…") },
                leadingIcon = { Icon(Icons.Default.Search, null) },
                trailingIcon = { Icon(Icons.Outlined.Tune, null) },
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            // Low Stocks card
            Card(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 12.dp)
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.35f)
                ),
                shape = RoundedCornerShape(12.dp),
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Low Stocks", style = MaterialTheme.typography.titleSmall)
                    Spacer(Modifier.weight(1f))
                    TextButton(onClick = {}) { Text("View All") }
                }
                LazyRow(
                    modifier = Modifier.padding(start = 12.dp, bottom = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(lowStocks) { it ->
                        LowStockChipCard(
                            name = it.name,
                            badgeCount = it.badgeCount,
                            thumb = it.thumbnailEmoji
                        )
                    }
                }
            }

            Text(
                "Current Inventory Items",
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp),
                style = MaterialTheme.typography.titleSmall
            )

            // Add inventory (light button row)
            ElevatedCard(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            ) {
                TextButton(
                    onClick = onAddInventory,
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(14.dp)
                ) {
                    Icon(Icons.Default.Add, null)
                    Spacer(Modifier.width(8.dp))
                    Text("Add Inventory")
                }
            }

            Spacer(Modifier.height(8.dp))

            // list
            val filtered = remember(query, items) {
                items.filter { query.isBlank() || it.name.contains(query, true) }
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(filtered, key = { it.id }) { item ->
                    InventoryRow(
                        item = item,
                        onClick = { onItemClick(item) },
                        trailing = { StatusChip(item.status) }
                    )
                    Divider()
                }
            }
        }
    }
}

// demo data
private fun sampleInventory() = listOf(
    InventoryItem("1", "All-Purpose Flour", "25 kg", StockStatus.InStock, "🪵"),
    InventoryItem("2", "Olive Oil", "18 L", StockStatus.InStock, "🫒"),
    InventoryItem("3", "Organic Avocados", "8 units", StockStatus.LowStock, "🥑", badgeCount = 8),
    InventoryItem("4", "Paper Napkins", "2 packs", StockStatus.LowStock, "🧻", badgeCount = 2),
    InventoryItem("5", "Roma Tomatoes", "0 kg", StockStatus.OutOfStock, "🍅", badgeCount = 0),
)
