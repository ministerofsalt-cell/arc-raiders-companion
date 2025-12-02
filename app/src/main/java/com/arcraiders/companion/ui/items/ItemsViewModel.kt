package com.arcraiders.companion.ui.items

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arcraiders.companion.data.api.RetrofitClient
import com.arcraiders.companion.data.model.Item
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ItemsUiState(
    val items: List<Item> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val currentPage: Int = 1,
    val hasMorePages: Boolean = true
)

class ItemsViewModel : ViewModel() {
    
    private val _uiState = MutableStateFlow(ItemsUiState())
    val uiState: StateFlow<ItemsUiState> = _uiState.asStateFlow()
    
    private val api = RetrofitClient.metaForgeApi
    
    init {
        loadItems()
    }
    
    fun loadItems(page: Int = 1) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            try {
                val response = api.getItems(page = page)
                _uiState.value = _uiState.value.copy(
                    items = if (page == 1) response.items else _uiState.value.items + response.items,
                    isLoading = false,
                    currentPage = page,
                    hasMorePages = page < response.pagination.totalPages
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Failed to load items: ${e.message}"
                )
            }
        }
    }
    
    fun loadNextPage() {
        if (!_uiState.value.isLoading && _uiState.value.hasMorePages) {
            loadItems(_uiState.value.currentPage + 1)
        }
    }
    
    fun retry() {
        loadItems(_uiState.value.currentPage)
    }
    
    fun refresh() {
        loadItems(1)
    }
}
