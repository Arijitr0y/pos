package org.kitchen.pos.app

import android.app.Application
import org.koin.core.context.startKoin

fun Application.startKoinIfNeeded() {
    // Idempotent start: call once
    startKoin {
        modules(appModules)
    }
}
