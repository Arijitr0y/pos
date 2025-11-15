@file:OptIn(ExperimentalMaterial3Api::class)

package org.kitchen.pos.features.orders

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
//import org.kitchen.pos.features.orders.components.BottomSummary // if you added it; otherwise remove
import org.kitchen.pos.features.orders.components.OrderRow
import org.kitchen.pos.features.orders.model.OrderStatus
import org.kitchen.pos.features.orders.model.PastOrder

@Composable
fun PastOrdersScreen(
    onViewDetails: (PastOrder) -> Unit = {},
    onPrintReceipt: (PastOrder) -> Unit = {}
) {
    val orders = remember { sampleOrders() }
    var query by remember { mutableStateOf("") }

    Scaffold(
            ) { inner ->
        Column(
            Modifier
                .padding(inner)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth(),
                placeholder = { Text("Search by Order ID, Customer…") },
                singleLine = true,
                leadingIcon = { Icon(Icons.Default.Search, null) },
                shape = RoundedCornerShape(12.dp)
            )

            Row(
                Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AssistChip(onClick = {}, label = { Text("All Filters") })
                FilterChip(selected = false, onClick = {}, label = { Text("Date Range") })
                FilterChip(selected = true, onClick = {}, label = { Text("Completed") })
                FilterChip(selected = false, onClick = {}, label = { Text("Refunded") })
                FilterChip(selected = false, onClick = {}, label = { Text("Voided") })
            }

            Divider()

            val filtered = remember(query, orders) {
                orders.filter {
                    query.isBlank() ||
                            it.id.contains(query, ignoreCase = true) ||
                            it.customer.contains(query, ignoreCase = true)
                }
            }

            LazyColumn(
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(
                    items = filtered,
                    key = { item -> item.id }
                ) { item ->
                    OrderRow(
                        order = item,
                        onViewDetails = { onViewDetails(item) },
                        onPrint = { onPrintReceipt(item) }
                    )
                    Divider()
                }
            }
        }
    }
}

// sample data
private fun sampleOrders() = listOf(
    PastOrder("#A12-B345", "Alex Johnson", "Oct 26, 2023, 2:15 PM", 24.50, OrderStatus.Completed),
    PastOrder("#C67-D890", "Walk-in", "Oct 26, 2023, 1:45 PM", 12.75, OrderStatus.Completed),
    PastOrder("#E23-F456", "Samantha Lee", "Oct 26, 2023, 11:30 AM", 35.00, OrderStatus.Refunded),
    PastOrder("#G78-H901", "Walk-in", "Oct 25, 2023, 5:05 PM", 42.10, OrderStatus.PartialRefund),
    PastOrder("#I24-J357", "Chris Evans", "Oct 25, 2023, 3:12 PM", 8.50, OrderStatus.Voided),
)
