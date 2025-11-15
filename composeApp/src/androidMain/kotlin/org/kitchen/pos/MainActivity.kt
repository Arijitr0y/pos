package org.kitchen.pos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import org.kitchen.pos.app.AppNavigation
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.kitchen.pos.app.appModules   // <-- your Koin modules list

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Start Koin ONCE (guarded)
        if (GlobalContext.getOrNull() == null) {
            startKoin {
                modules(appModules)
            }
        }

        setContent {
            MaterialTheme { // <-- use MaterialTheme instead of AppTheme
                AppRoot()
            }
        }
    }
}

@Composable
private fun AppRoot() {
    AppNavigation() // your root composable
}
