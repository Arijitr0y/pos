package org.kitchen.pos.features.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.kitchen.pos.features.settings.components.SettingsRow

@Composable
fun SettingsScreen(
    onGeneral: () -> Unit = {},
    onProfile: () -> Unit = {},
    onPosPrefs: () -> Unit = {},
    onPrinters: () -> Unit = {},
    onSecurity: () -> Unit = {},
    onSubscription: () -> Unit = {},
    onAppSettings: () -> Unit = {},
    onAbout: () -> Unit = {},
) {
    Scaffold(
        topBar = {}
    ) { inner ->
        Column(
            Modifier
                .padding(inner)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(12.dp)
        ) {
            SettingsRow(
                icon = Icons.Filled.Tune,
                title = "General Settings",
                subtitle = "Language, theme, and notifications",
                trailing = { Icon(Icons.Filled.KeyboardArrowRight, null) },
                onClick = onGeneral
            )
            Spacer(Modifier.height(10.dp))

            SettingsRow(
                icon = Icons.Filled.Person,
                title = "Profile Settings",
                subtitle = "Name, contact details, and profile picture",
                trailing = { Icon(Icons.Filled.KeyboardArrowRight, null) },
                onClick = onProfile
            )
            Spacer(Modifier.height(10.dp))

            SettingsRow(
                icon = Icons.Filled.Settings,
                title = "POS Preferences",
                subtitle = "Customize receipts, payments, and tips",
                trailing = { Icon(Icons.Filled.KeyboardArrowRight, null) },
                onClick = onPosPrefs
            )
            Spacer(Modifier.height(10.dp))

            SettingsRow(
                icon = Icons.Filled.Print,
                title = "Printer Settings",
                subtitle = "Manage receipt and kitchen printers",
                trailing = { Icon(Icons.Filled.KeyboardArrowRight, null) },
                onClick = onPrinters
            )
            Spacer(Modifier.height(10.dp))

            SettingsRow(
                icon = Icons.Filled.Lock,
                title = "Security & Access",
                subtitle = "PIN, biometrics, and session timeouts",
                trailing = { Icon(Icons.Filled.KeyboardArrowRight, null) },
                onClick = onSecurity
            )
            Spacer(Modifier.height(10.dp))

            SettingsRow(
                icon = Icons.Filled.CreditCard,
                title = "Subscription",
                subtitle = "Manage your POS service plan",
                trailing = { Icon(Icons.Filled.KeyboardArrowRight, null) },
                onClick = onSubscription
            )
            Spacer(Modifier.height(10.dp))

            SettingsRow(
                icon = Icons.Filled.Apps,
                title = "App Settings",
                subtitle = "Logout and change password",
                trailing = { Icon(Icons.Filled.KeyboardArrowRight, null) },
                onClick = onAppSettings
            )
            Spacer(Modifier.height(10.dp))

            SettingsRow(
                icon = Icons.Filled.HelpOutline,
                title = "About & Support",
                subtitle = "App version, help center, and feedback",
                trailing = { Icon(Icons.Filled.KeyboardArrowRight, null) },
                onClick = onAbout
            )

            Spacer(Modifier.height(16.dp))
        }
    }
}
