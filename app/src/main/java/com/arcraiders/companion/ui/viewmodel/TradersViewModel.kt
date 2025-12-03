package com.arcraiders.companion.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arcraiders.companion.data.model.Trader
import com.arcraiders.companion.data.repository.TradersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for managing trader data
 * Provides trader list and search functionality
 */
@HiltViewModel
class TradersViewModel @Inject constructor(
    private val repository: TradersRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<TradersUiState>(TradersUiState.Loading)
    val uiState: StateFlow<TradersUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        loadTraders()
    }

    /**
     * Load all traders from repository
     */
    fun loadTraders() {
        viewModelScope.launch {
            _uiState.value = TradersUiState.Loading
            
            repository.getTraders().collect { traders ->
                if (traders.isEmpty()) {
                    _uiState.value = TradersUiState.Error("No traders found")
                } else {
                    _uiState.value = TradersUiState.Success(traders)
                }
            }
        }
    }

    /**
     * Search traders by item name
     */
    fun searchByItem(itemName: String) {
        _searchQuery.value = itemName
        
        if (itemName.isBlank()) {
            loadTraders()
            return
        }
        
        viewModelScope.launch {
            _uiState.value = TradersUiState.Loading
            
            repository.searchTradersByItem(itemName).collect { traders ->
                if (traders.isEmpty()) {
                    _uiState.value = TradersUiState.Error("No traders found selling '$itemName'")
                } else {
                    _uiState.value = TradersUiState.Success(traders)
                }
            }
        }
    }

    /**
     * Get specific trader by name
     */
    fun getTrader(traderName: String) {
        viewModelScope.launch {
            _uiState.value = TradersUiState.Loading
            
            repository.getTraderByName(traderName).collect { trader ->
                if (trader == null) {
                    _uiState.value = TradersUiState.Error("Trader '$traderName' not found")
                } else {
                    _uiState.value = TradersUiState.Success(listOf(trader))
                }
            }
        }
    }

    /**
     * Clear search and reload all traders
     */
    fun clearSearch() {
        _searchQuery.value = ""
        loadTraders()
    }

    /**
     * Retry loading traders
     */
    fun retry() {
        if (_searchQuery.value.isBlank()) {
            loadTraders()
        } else {
            searchByItem(_searchQuery.value)
        }
    }
}

/**
 * UI state for traders screen
 */
sealed class TradersUiState {
    object Loading : TradersUiState()
    data class Success(val traders: List<Trader>) : TradersUiState()
    data class Error(val message: String) : TradersUiState()
}
