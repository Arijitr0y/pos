package org.kitchen.pos.features.inventory.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LowStockChipCard(
    name: String,
    badgeCount: Int,
    thumb: String
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        tonalElevation = 1.dp
    ) {
        Column(
            Modifier
                .width(120.dp)
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                Modifier
                    .size(70.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(thumb)
                // badge
                if (badgeCount >= 0) {
                    Box(
                        Modifier
                            .align(Alignment.TopEnd)
                            .background(Color(0xFFFFE082), RoundedCornerShape(10.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) { Text(badgeCount.toString(), style = MaterialTheme.typography.labelSmall) }
                }
            }
            Spacer(Modifier.height(6.dp))
            Text(name, style = MaterialTheme.typography.bodySmall)
        }
    }
}
