package com.arcraiders.companion.ui.quests

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arcraiders.companion.data.model.Quest
import com.arcraiders.companion.data.repository.QuestsRepository
import kotlinx.coroutines.launch

data class QuestsUiState(
    val quests: List<Quest> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isRefreshing: Boolean = false
)

class QuestsViewModel(
    private val repository: QuestsRepository
) : ViewModel() {
    
    var uiState by mutableStateOf(QuestsUiState())
        private set
    
    init {
        loadQuests()
    }
    
    fun loadQuests() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, errorMessage = null)
            try {
                repository.getQuests().collect { quests ->
                    uiState = uiState.copy(
                        quests = quests,
                        isLoading = false,
                        isRefreshing = false
                    )
                }
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    isRefreshing = false,
                    errorMessage = e.message ?: "Unknown error occurred"
                )
            }
        }
    }
    
    fun refresh() {
        viewModelScope.launch {
            uiState = uiState.copy(isRefreshing = true, errorMessage = null)
            try {
                repository.refreshQuests()
                // Data will be updated through the Flow in loadQuests
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isRefreshing = false,
                    errorMessage = e.message ?: "Failed to refresh"
                )
            }
        }
    }
    
    fun updateQuestProgress(questId: String, progress: Int) {
        viewModelScope.launch {
            try {
                // Update quest progress locally
                val updatedQuests = uiState.quests.map { quest ->
                    if (quest.id == questId) {
                        quest.copy(progress = progress)
                    } else {
                        quest
                    }
                }
                uiState = uiState.copy(quests = updatedQuests)
            } catch (e: Exception) {
                uiState = uiState.copy(
                    errorMessage = e.message ?: "Failed to update progress"
                )
            }
        }
    }
}
