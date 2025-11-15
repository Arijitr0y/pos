package org.kitchen.pos.features.orders.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.ReceiptLong
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.kitchen.pos.features.orders.model.PastOrder

@Composable
fun OrderRow(
    order: PastOrder,
    onViewDetails: () -> Unit,
    onPrint: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FilledTonalIconButton(onClick = onViewDetails) {
            Icon(Icons.Outlined.ReceiptLong, contentDescription = null)
        }
        Spacer(Modifier.width(12.dp))

        Column(Modifier.weight(1f)) {
            Text("Order ${order.id}", style = MaterialTheme.typography.titleMedium)
            Text(order.dateTime, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(order.customer, style = MaterialTheme.typography.bodySmall)
            Spacer(Modifier.height(8.dp))
            TextButton(onClick = onViewDetails) { Text("View Details") }
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(
                "$${"%.2f".format(order.amount)}",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
            )
            Spacer(Modifier.height(6.dp))
            StatusBadge(order.status)
            Spacer(Modifier.height(8.dp))
            OutlinedButton(onClick = onPrint) { Text("Print Receipt") }
            IconButton(onClick = {}) { Icon(Icons.Outlined.MoreVert, null) }
        }
    }
}
