package org.kitchen.pos.features.orders.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.kitchen.pos.features.orders.model.OrderStatus

@Composable
fun StatusBadge(status: OrderStatus) {
    val (bg, fg, text) = when (status) {
        OrderStatus.Completed     -> Triple(Color(0xFFDFF5E1), Color(0xFF217A37), "Completed")
        OrderStatus.Refunded      -> Triple(Color(0xFFFFE4E6), Color(0xFFB42318), "Refunded")
        OrderStatus.PartialRefund -> Triple(Color(0xFFFFF4E5), Color(0xFF8A4D00), "Partial Refund")
        OrderStatus.Voided        -> Triple(Color(0xFFEDEEF1), Color(0xFF6B7280), "Voided")
    }
    Box(
        Modifier
            .background(bg, RoundedCornerShape(50))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) { Text(text, color = fg, style = MaterialTheme.typography.labelSmall) }
}
