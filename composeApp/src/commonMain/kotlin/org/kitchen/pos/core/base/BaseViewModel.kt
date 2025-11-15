package org.kitchen.pos.core.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

// Simple KMP ViewModel (no AndroidX dependency)
abstract class BaseViewModel {
    protected val vmJob = SupervisorJob()
    protected val scope = CoroutineScope(Dispatchers.Main.immediate + vmJob)

    open fun onCleared() {
        scope.cancel()
    }
}
