package org.kitchen.pos.features.orders

import kotlinx.coroutines.launch
import org.kitchen.pos.core.base.BaseViewModel

class OrdersViewModel(
    private val repo: OrdersRepository
) : BaseViewModel() {

    private var _state = kotlinx.coroutines.flow.MutableStateFlow(OrdersState())
    val state = _state

    fun refresh() {
        _state.value = _state.value.copy(isLoading = true, error = null)
        scope.launch {
            runCatching { repo.loadOrders() }
                .onSuccess { list ->
                    _state.value = OrdersState(items = list, isLoading = false)
                }
                .onFailure { e ->
                    _state.value = OrdersState(error = e.message, isLoading = false)
                }
        }
    }
}
