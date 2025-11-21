package org.kitchen.pos.features.myshop

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.kitchen.pos.data.AppRepositories
import org.kitchen.pos.data.shop.Shop

// ...

@Composable
fun MyShopScreen(
    modifier: Modifier = Modifier
) {
    val viewModel = remember {
        MyShopViewModel(AppRepositories.shopRepository)
    }
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        if (uiState.isLoading && uiState.shop == null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            MyShopScreenContent(
                state = uiState,
                onStartEditing = { viewModel.startEditing() },
                onCancelEditing = { viewModel.cancelEditing() },
                onSave = { viewModel.saveShop() },
                onUpdateShop = { transform -> viewModel.updateShopDraft(transform) }
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyShopRoute(
    viewModel: MyShopViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Shop") },
                actions = {
                    IconButton(onClick = { /* later: trigger manual sync */ }) {
                        Icon(
                            imageVector = Icons.Default.Sync,
                            contentDescription = "Sync"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (uiState.isLoading && uiState.shop == null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                MyShopScreenContent(
                    state = uiState,
                    onStartEditing = { viewModel.startEditing() },
                    onCancelEditing = { viewModel.cancelEditing() },
                    onSave = { viewModel.saveShop() },
                    onUpdateShop = { transform -> viewModel.updateShopDraft(transform) }
                )
            }
        }
    }
}

@Composable
private fun MyShopScreenContent(
    state: MyShopUiState,
    onStartEditing: () -> Unit,
    onCancelEditing: () -> Unit,
    onSave: () -> Unit,
    onUpdateShop: ((Shop) -> Shop) -> Unit,
) {
    val shop = state.shop ?: return

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Profile Card
        item {
            ShopProfileCard(
                shop = shop,
                isEditing = state.isEditing,
                isSaving = state.isSaving,
                errorMessage = state.errorMessage,
                onStartEditing = onStartEditing,
                onCancelEditing = onCancelEditing,
                onSave = onSave,
                onUpdateShop = onUpdateShop
            )
        }

        // Stats card
        item {
            ShopStatsCard(stats = state.stats)
        }

        // Business settings
        item {
            BusinessSettingsCard(
                shop = shop,
                isEditing = state.isEditing,
                onUpdateShop = onUpdateShop
            )
        }

        // Security / backup info
        item {
            SecurityBackupCard()
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun ShopProfileCard(
    shop: Shop,
    isEditing: Boolean,
    isSaving: Boolean,
    errorMessage: String?,
    onStartEditing: () -> Unit,
    onCancelEditing: () -> Unit,
    onSave: () -> Unit,
    onUpdateShop: ((Shop) -> Shop) -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Shop Profile",
                    style = MaterialTheme.typography.titleMedium
                )
                if (!isEditing) {
                    IconButton(onClick = onStartEditing) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit"
                        )
                    }
                }
            }

            if (!isEditing) {
                Text(text = shop.name, style = MaterialTheme.typography.titleLarge)
                if (shop.ownerName.isNotBlank()) {
                    Text(
                        text = "Owner: ${shop.ownerName}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                if (shop.phone.isNotBlank()) {
                    Text(text = "Phone: ${shop.phone}", style = MaterialTheme.typography.bodyMedium)
                }
                if (shop.email.isNotBlank()) {
                    Text(text = "Email: ${shop.email}", style = MaterialTheme.typography.bodyMedium)
                }
                if (shop.addressLine1.isNotBlank()) {
                    Text(
                        text = buildString {
                            append(shop.addressLine1)
                            if (shop.addressLine2.isNotBlank()) {
                                append(", ")
                                append(shop.addressLine2)
                            }
                            if (shop.city.isNotBlank()) {
                                append(", ")
                                append(shop.city)
                            }
                            if (shop.state.isNotBlank()) {
                                append(", ")
                                append(shop.state)
                            }
                            if (shop.pincode.isNotBlank()) {
                                append(" - ")
                                append(shop.pincode)
                            }
                        },
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                if (!shop.gstin.isNullOrBlank()) {
                    Text(
                        text = "GSTIN: ${shop.gstin}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            } else {
                // Editable fields
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = shop.name,
                        onValueChange = { value ->
                            onUpdateShop { it.copy(name = value) }
                        },
                        label = { Text("Shop Name") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = shop.ownerName,
                        onValueChange = { value ->
                            onUpdateShop { it.copy(ownerName = value) }
                        },
                        label = { Text("Owner Name") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = shop.phone,
                        onValueChange = { value ->
                            onUpdateShop { it.copy(phone = value) }
                        },
                        label = { Text("Phone") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Phone
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = shop.email,
                        onValueChange = { value ->
                            onUpdateShop { it.copy(email = value) }
                        },
                        label = { Text("Email") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = shop.addressLine1,
                        onValueChange = { value ->
                            onUpdateShop { it.copy(addressLine1 = value) }
                        },
                        label = { Text("Address Line 1") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = shop.addressLine2,
                        onValueChange = { value ->
                            onUpdateShop { it.copy(addressLine2 = value) }
                        },
                        label = { Text("Address Line 2") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = shop.city,
                            onValueChange = { value ->
                                onUpdateShop { it.copy(city = value) }
                            },
                            label = { Text("City") },
                            singleLine = true,
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = shop.state,
                            onValueChange = { value ->
                                onUpdateShop { it.copy(state = value) }
                            },
                            label = { Text("State") },
                            singleLine = true,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = shop.pincode,
                            onValueChange = { value ->
                                onUpdateShop { it.copy(pincode = value) }
                            },
                            label = { Text("Pincode") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = shop.gstin.orEmpty(),
                            onValueChange = { value ->
                                onUpdateShop { it.copy(gstin = value.ifBlank { null }) }
                            },
                            label = { Text("GSTIN (optional)") },
                            singleLine = true,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    if (errorMessage != null) {
                        Text(
                            text = errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(onClick = onCancelEditing, enabled = !isSaving) {
                            Text("Cancel")
                        }
                        Spacer(modifier = Modifier.height(0.dp))
                        Button(onClick = onSave, enabled = !isSaving) {
                            if (isSaving) {
                                CircularProgressIndicator(
                                    modifier = Modifier.height(18.dp),
                                    strokeWidth = 2.dp
                                )
                            } else {
                                Text("Save")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ShopStatsCard(stats: org.kitchen.pos.data.shop.ShopStats) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Overview",
                style = MaterialTheme.typography.titleMedium
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatItem(label = "Products", value = stats.totalProducts.toString())
                StatItem(label = "Low Stock", value = stats.lowStockItems.toString())
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatItem(
                    label = "Today Sales",
                    value = "₹${"%.2f".format(stats.todaySalesAmount)}"
                )
                StatItem(
                    label = "Pending Dues",
                    value = "₹${"%.2f".format(stats.pendingDuesAmount)}"
                )
            }
        }
    }
}

@Composable
private fun StatItem(label: String, value: String) {
    Column {
        Text(text = value, style = MaterialTheme.typography.titleMedium)
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun BusinessSettingsCard(
    shop: Shop,
    isEditing: Boolean,
    onUpdateShop: ((Shop) -> Shop) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Business Settings",
                style = MaterialTheme.typography.titleMedium
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Tax inclusive pricing")
                Switch(
                    checked = shop.taxInclusivePricing,
                    onCheckedChange = if (!isEditing) null else { checked ->
                        onUpdateShop { it.copy(taxInclusivePricing = checked) }
                    }
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Allow negative stock")
                Switch(
                    checked = shop.allowNegativeStock,
                    onCheckedChange = if (!isEditing) null else { checked ->
                        onUpdateShop { it.copy(allowNegativeStock = checked) }
                    }
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Currency:")
                AssistChip(
                    onClick = { /* later: open currency picker */ },
                    label = { Text(shop.currencyCode) },
                    colors = AssistChipDefaults.assistChipColors()
                )
            }
        }
    }
}

@Composable
private fun SecurityBackupCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Security & Backup",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "• Offline-first: All data is stored locally in an encrypted database.\n" +
                        "• When internet is available, data will sync with Supabase.\n" +
                        "• Your shop data is centralized and shared across all screens.",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
