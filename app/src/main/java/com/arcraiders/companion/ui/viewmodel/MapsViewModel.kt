package com.arcraiders.companion.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arcraiders.companion.data.model.GameMap
import com.arcraiders.companion.data.model.POI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MapsUiState(
    val maps: List<GameMap> = emptyList(),
    val selectedMap: GameMap? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class MapsViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(MapsUiState())
    val uiState: StateFlow<MapsUiState> = _uiState.asStateFlow()

    init {
        loadMaps()
    }

    private fun loadMaps() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                // Sample maps data
                val maps = listOf(
                    GameMap(
                        "1",
                        "Frost Valley",
                        "assets/maps/frost_valley.png",
                        listOf(
                            POI("poi1", "Supply Cache", 45.5, 67.3, "loot"),
                            POI("poi2", "Safe Zone", 23.1, 89.5, "safe"),
                            POI("poi3", "Boss Arena", 78.2, 34.6, "danger")
                        )
                    ),
                    GameMap(
                        "2",
                        "Desert Ruins",
                        "assets/maps/desert_ruins.png",
                        listOf(
                            POI("poi4", "Trader Post", 56.7, 45.2, "trader"),
                            POI("poi5", "Ancient Structure", 34.5, 78.9, "loot")
                        )
                    )
                )
                _uiState.value = _uiState.value.copy(
                    maps = maps,
                    selectedMap = maps.firstOrNull(),
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message
                )
            }
        }
    }

    fun selectMap(map: GameMap) {
        _uiState.value = _uiState.value.copy(selectedMap = map)
    }

    fun refresh() {
        loadMaps()
    }
}
