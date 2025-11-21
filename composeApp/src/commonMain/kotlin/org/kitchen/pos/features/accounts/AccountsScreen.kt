package org.kitchen.pos.features.accounts

import androidx.compose.ui.graphics.Color

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.kitchen.pos.features.accounts.components.SectionCard
import org.kitchen.pos.features.accounts.components.StatCard
import org.kitchen.pos.features.accounts.components.TransactionRow

@Composable
fun AccountsScreen(
    onAdd: () -> Unit = {},
    onViewAllTransactions: () -> Unit = {},
    onOpenReport: (String) -> Unit = {}
) {
    var tab by remember { mutableStateOf(0) } // 0 Daily, 1 Weekly, 2 Monthly

    Scaffold(

        floatingActionButton = {
            FloatingActionButton(onClick = onAdd) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { inner ->
        Column(
            Modifier
                .padding(inner)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(12.dp)
        ) {
            // Segmented tabs
            SingleChoiceSegmentedButtonRow(Modifier.fillMaxWidth()) {
                val labels = listOf("Daily", "Weekly", "Monthly")
                labels.forEachIndexed { i, label ->
                    SegmentedButton(
                        selected = tab == i,
                        onClick = { tab = i },
                        label = { Text(label) },
                        shape = SegmentedButtonDefaults.itemShape(i, labels.size)
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            // Top stats row
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatCard(
                    title = "Revenue",
                    amount = "$1,250.75",
                    deltaText = "↑ +5.2% vs yesterday",
                    deltaColor = Color(0xFF1B7840),
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Expenses",
                    amount = "$430.50",
                    deltaText = "↓ -2.1% vs yesterday",
                    deltaColor = Color(0xFFB42318),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(12.dp))

            // Profit / Loss wide card
            StatCard(
                title = "Profit / Loss",
                amount = "$820.25",
                deltaText = "↑ +8.3% vs yesterday",
                deltaColor = Color(0xFF1B7840),
                wide = true
            )

            Spacer(Modifier.height(12.dp))

            // Daily Transactions
            SectionCard(
                title = "Daily Transactions",
                actionText = "View All Transactions",
                onAction = onViewAllTransactions
            ) {
                val txs = listOf(
                    Triple("#TRX-78523", "12:34 PM", "+$45.50"),
                    Triple("#TRX-78522", "12:29 PM", "+$22.00"),
                    Triple("#TRX-78521", "12:15 PM", "+$12.75")
                )
                txs.forEach { (id, time, amt) ->
                    TransactionRow(id = id, time = time, amount = amt)
                    if (id != txs.last().first) Divider()
                }
            }

            Spacer(Modifier.height(12.dp))

            // General Ledger & Journal
            SectionCard(
                title = "General Ledger & Journal",
                subtitle = "View and manage entries",
                trailingChevron = true,
                onClick = { onOpenReport("ledger") }
            )

            Spacer(Modifier.height(12.dp))

            // Reporting Suite (grid)
            Text("Reporting Suite", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(8.dp))
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    ReportTile("Profit & Loss", modifier = Modifier.weight(1f)) { onOpenReport("pnl") }
                    ReportTile("Cash Flow",     modifier = Modifier.weight(1f)) { onOpenReport("cashflow") }
                }
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    ReportTile("Balance Sheet", modifier = Modifier.weight(1f)) { onOpenReport("balancesheet") }
                    ReportTile("Custom Report", modifier = Modifier.weight(1f)) { onOpenReport("custom") }
                }

            }

            Spacer(Modifier.height(24.dp))
        }
    }
}


@Composable
private fun ReportTile(
    label: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = modifier.height(84.dp),
        onClick = onClick
    ) {
        Box(Modifier.fillMaxSize().padding(16.dp)) {
            Text(label, style = MaterialTheme.typography.titleSmall)
        }
    }
}
