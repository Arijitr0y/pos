package org.kitchen.pos.features.inventory.components

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
import org.kitchen.pos.features.inventory.model.StockStatus

@Composable
fun StatusChip(status: StockStatus) {
    val (bg, fg, text) = when (status) {
        StockStatus.InStock    -> Triple(Color(0xFFE6F5EC), Color(0xFF1B7840), "In Stock")
        StockStatus.LowStock   -> Triple(Color(0xFFFFF3D9), Color(0xFF835B00), "Low Stock")
        StockStatus.OutOfStock -> Triple(Color(0xFFFFE8E9), Color(0xFFB32522), "Out of Stock")
    }
    Box(
        Modifier
            .background(bg, RoundedCornerShape(50))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) { Text(text, color = fg, style = MaterialTheme.typography.labelSmall) }
}
