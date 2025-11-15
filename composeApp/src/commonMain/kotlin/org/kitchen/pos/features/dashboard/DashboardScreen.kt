@file:Suppress("UnusedImport")

package com.example.pos.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreTime
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Assessment
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun DashboardScreen(
    storeName: String = "The Daily Grind",
    todaySales: String = "$1,250.75",
    cashInDrawer: String = "$320.00",
    numberOfBills: String = "82",
    itemsSold: String = "247",
    pendingOrders: String = "6",
    oldestPendingAge: String = "12 min ago",
    dateText: String = LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM d, yyyy")),
    onMenuClick: () -> Unit = {},
    onRefreshPending: () -> Unit = {},
    onStartNewBill: () -> Unit = {},
    onReportsClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
) {
    Surface(color = MaterialTheme.colorScheme.surface) {
        Box(Modifier.fillMaxSize()) {

            Column(
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {

                // Top bar (hamburger + date)
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = dateText,
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }

                Spacer(Modifier.height(4.dp))

                // Title
                Text(
                    text = storeName,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                )

                Spacer(Modifier.height(20.dp))

                // KPI grid (2 columns)
                MetricsGrid(
                    todaySales = todaySales,
                    cashInDrawer = cashInDrawer,
                    numberOfBills = numberOfBills,
                    itemsSold = itemsSold
                )

                Spacer(Modifier.height(18.dp))

                // Pending Orders block
                PendingOrdersCard(
                    count = pendingOrders,
                    oldest = oldestPendingAge,
                    onRefresh = onRefreshPending
                )

                Spacer(Modifier.weight(1f))

                // CTA + bottom pills
                StartNewBillButton(onClick = onStartNewBill)

                Spacer(Modifier.height(12.dp))

                BottomPills(
                    onReportsClick = onReportsClick,
                    onSettingsClick = onSettingsClick
                )

                Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
            }
        }
    }
}

@Composable
private fun MetricsGrid(
    todaySales: String,
    cashInDrawer: String,
    numberOfBills: String,
    itemsSold: String,
) {
    Column(Modifier.fillMaxWidth()) {
        Row(Modifier.fillMaxWidth()) {
            MetricTile(
                label = "Today's Sales",
                value = todaySales,
                labelColor = Color(0xFF1A73E8), // Google-ish blue
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(16.dp))
            MetricTile(
                label = "Cash in Drawer",
                value = cashInDrawer,
                labelColor = Color(0xFF2E7D32), // green
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(Modifier.height(18.dp))
        Row(Modifier.fillMaxWidth()) {
            MetricTile(
                label = "No. of Bills",
                value = numberOfBills,
                labelColor = Color(0xFFF9A825), // amber
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(16.dp))
            MetricTile(
                label = "Items Sold",
                value = itemsSold,
                labelColor = Color(0xFFC62828), // red
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun MetricTile(
    label: String,
    value: String,
    labelColor: Color,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge.copy(
                color = labelColor,
                fontWeight = FontWeight.Medium
            )
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = (-0.5).sp
            )
        )
    }
}

@Composable
private fun PendingOrdersCard(
    count: String,
    oldest: String,
    onRefresh: () -> Unit
) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f)) {
            Text(
                text = "Pending Orders",
                style = MaterialTheme.typography.labelLarge.copy(
                    color = Color(0xFF1A73E8),
                    fontWeight = FontWeight.Medium
                )
            )
            Spacer(Modifier.height(6.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = count,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(Modifier.width(12.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.MoreTime,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(
                        text = "Oldest: $oldest",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }
        }

        // refresh icon button
        FilledTonalIconButton(
            onClick = onRefresh,
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
        ) {
            Icon(Icons.Default.Refresh, contentDescription = "Refresh")
        }
    }
}

@Composable
private fun StartNewBillButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Outlined.Add, contentDescription = null)
            Spacer(Modifier.width(10.dp))
            Text(
                text = "Start New Bill",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
            )
        }
    }
}

@Composable
private fun BottomPills(
    onReportsClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        PillButton(
            text = "Reports",
            icon = Icons.Outlined.Assessment,
            modifier = Modifier.weight(1f),
            onClick = onReportsClick
        )
        PillButton(
            text = "Settings",
            icon = Icons.Outlined.Settings,
            modifier = Modifier.weight(1f),
            onClick = onSettingsClick
        )
    }
}

@Composable
private fun PillButton(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(18.dp)
    Row(
        modifier
            .clip(shape)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.width(10.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            ),
            textAlign = TextAlign.Center
        )
    }
}
