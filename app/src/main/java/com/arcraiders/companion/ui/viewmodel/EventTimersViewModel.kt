package com.arcraiders.companion.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arcraiders.companion.data.model.EventTimer
import com.arcraiders.companion.data.repository.EventTimersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for managing event timer state
 * Provides live countdown updates for event timers
 */
@HiltViewModel
class EventTimersViewModel @Inject constructor(
    private val repository: EventTimersRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<EventTimersUiState>(EventTimersUiState.Loading)
    val uiState: StateFlow<EventTimersUiState> = _uiState.asStateFlow()

    init {
        loadEventTimers()
    }

    /**
     * Load event timers from repository
     */
    fun loadEventTimers() {
        viewModelScope.launch {
            _uiState.value = EventTimersUiState.Loading
            
            repository.getEventTimers().collect { timers ->
                if (timers.isEmpty()) {
                    _uiState.value = EventTimersUiState.Error("No events found")
                } else {
                    _uiState.value = EventTimersUiState.Success(timers)
                    // Start countdown updates
                    startCountdownUpdates(timers)
                }
            }
        }
    }

    /**
     * Update countdown every second
     */
    private fun startCountdownUpdates(timers: List<EventTimer>) {
        viewModelScope.launch {
            while (true) {
                delay(1000) // Update every second
                
                // Recalculate countdowns
                val updatedTimers = timers.map { timer ->
                    val nextTime = repository.calculateNextEventTime(timer)
                    val countdown = if (nextTime != null) {
                        repository.formatCountdown(nextTime - System.currentTimeMillis())
                    } else {
                        "No upcoming events"
                    }
                    
                    timer.copy(countdown = countdown)
                }
                
                _uiState.value = EventTimersUiState.Success(updatedTimers)
            }
        }
    }

    /**
     * Retry loading events
     */
    fun retry() {
        loadEventTimers()
    }
}

/**
 * UI state for event timers screen
 */
sealed class EventTimersUiState {
    object Loading : EventTimersUiState()
    data class Success(val timers: List<EventTimer>) : EventTimersUiState()
    data class Error(val message: String) : EventTimersUiState()
}
