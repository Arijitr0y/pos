package org.kitchen.pos.features.myshop

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.kitchen.pos.data.shop.Shop
import org.kitchen.pos.data.shop.ShopRepository

/**
 * Simple KMP ViewModel (no AndroidX dependency) so it works on all targets.
 * Lifetime is controlled by the caller (screen / DI).
 */
class MyShopViewModel(
    private val repository: ShopRepository,
    private val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
) {

    private val _uiState = MutableStateFlow(MyShopUiState())
    val uiState: StateFlow<MyShopUiState> = _uiState

    init {
        observeShop()
        observeStats()
    }

    private fun observeShop() {
        scope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val initial = repository.getOrCreateDefaultShop()
            _uiState.update { it.copy(shop = initial, isLoading = false) }

            repository.shopFlow.collectLatest { shop ->
                _uiState.update { state ->
                    state.copy(
                        shop = shop ?: state.shop,
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun observeStats() {
        scope.launch {
            repository.statsFlow.collectLatest { stats ->
                _uiState.update { it.copy(stats = stats) }
            }
        }
    }

    fun startEditing() {
        _uiState.update { it.copy(isEditing = true, errorMessage = null) }
    }

    fun cancelEditing() {
        _uiState.update { it.copy(isEditing = false, errorMessage = null) }
    }

    fun updateShopDraft(transform: (Shop) -> Shop) {
        val current = _uiState.value.shop ?: return
        _uiState.update { it.copy(shop = transform(current)) }
    }

    fun saveShop() {
        val current = _uiState.value.shop ?: return
        scope.launch {
            try {
                _uiState.update { it.copy(isSaving = true, errorMessage = null) }
                repository.upsertShop(current)
                _uiState.update { it.copy(isSaving = false, isEditing = false) }
            } catch (t: Throwable) {
                _uiState.update {
                    it.copy(
                        isSaving = false,
                        errorMessage = t.message ?: "Failed to save shop"
                    )
                }
            }
        }
    }
}
