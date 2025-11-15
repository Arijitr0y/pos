package org.kitchen.pos

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.kitchen.pos.app.App
import org.kitchen.pos.app.appModules
import org.koin.core.context.startKoin

fun main() = application {
    // Start DI once
    startKoin { modules(appModules) }

    Window(onCloseRequest = ::exitApplication, title = "POS") {
        App()
    }
}
