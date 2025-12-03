package com.arcraiders.companion.data.repository

import com.arcraiders.companion.data.api.MetaforgeApi
import com.arcraiders.companion.data.dao.QuestDao
import com.arcraiders.companion.data.model.Quest
import com.arcraiders.companion.data.model.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuestsRepository @Inject constructor(
    private val api: MetaforgeApi,
    private val dao: QuestDao
) {

    /**
     * Get all quests from local database and refresh from API
     * Returns Flow that emits cached data first, then updated data from API
     */
    fun getQuests(): Flow<List<Quest>> = flow {
        // First emit cached data from database
        val cachedQuests = dao.getAllQuests().map { entities ->
            entities.map { it.toQuest() }
        }
        
        // Emit cached data immediately
        cachedQuests.collect { emit(it) }
        
        // Then fetch fresh data from API and update cache
        try {
            refreshQuests()
        } catch (e: Exception) {
            // If API call fails, we still have cached data
            // Log error but don't crash
        }
    }

    /**
     * Get a specific quest by ID
     */
    fun getQuestById(id: String): Flow<Quest?> = 
        dao.getQuestById(id).map { it?.toQuest() }

    /**
     * Refresh quests from API and update local database
     */
    suspend fun refreshQuests() {
        try {
            val quests = api.getQuests()
            dao.insertAll(quests.map { it.toEntity() })
        } catch (e: Exception) {
            // Handle error - could log or throw depending on requirements
            throw e
        }
    }

    /**
     * Toggle quest completion status
     */
    suspend fun toggleQuestCompletion(questId: String): Boolean {
        return try {
            val quest = dao.getQuestByIdSync(questId)
            quest?.let {
                val updatedQuest = it.copy(isCompleted = !it.isCompleted)
                dao.update(updatedQuest)
                updatedQuest.isCompleted
            } ?: false
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Get quests filtered by type
     */
    fun getQuestsByType(type: String): Flow<List<Quest>> =
        dao.getQuestsByType(type).map { entities ->
            entities.map { it.toQuest() }
        }

    /**
     * Get completed quests
     */
    fun getCompletedQuests(): Flow<List<Quest>> =
        dao.getCompletedQuests().map { entities ->
            entities.map { it.toQuest() }
        }

    /**
     * Get incomplete quests
     */
    fun getIncompleteQuests(): Flow<List<Quest>> =
        dao.getIncompleteQuests().map { entities ->
            entities.map { it.toQuest() }
        }

    /**
     * Update quest progress (for objectives tracking)
     */
    suspend fun updateQuestProgress(questId: String, progress: Int) {
        try {
            dao.updateQuestProgress(questId, progress)
        } catch (e: Exception) {
            // Handle error
        }
    }

    /**
     * Mark quest as completed
     */
    suspend fun markQuestCompleted(questId: String) {
        try {
            dao.markQuestCompleted(questId)
        } catch (e: Exception) {
            // Handle error
        }
    }

    /**
     * Reset all quest progress (for testing or reset functionality)
     */
    suspend fun resetAllQuests() {
        try {
            dao.resetAllQuests()
        } catch (e: Exception) {
            // Handle error
        }
    }

    /**
     * Delete a quest from local database
     */
    suspend fun deleteQuest(questId: String) {
        try {
            dao.deleteQuestById(questId)
        } catch (e: Exception) {
            // Handle error
        }
    }

    /**
     * Clear all quests from local database
     */
    suspend fun clearAllQuests() {
        try {
            dao.deleteAll()
        } catch (e: Exception) {
            // Handle error
        }
    }
}
