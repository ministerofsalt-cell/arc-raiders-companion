package com.arcraiders.companion.data.repository

import com.arcraiders.companion.data.api.MetaForgeApi
import com.arcraiders.companion.data.dao.QuestDao
import com.arcraiders.companion.data.model.Quest
import com.arcraiders.companion.data.model.toEntity
import com.arcraiders.companion.data.model.toQuest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuestsRepository @Inject constructor(
    private val api: MetaForgeApi,
    private val dao: QuestDao
) {
    fun getQuests(): Flow<List<Quest>> = dao.getAllQuests().map { entities ->
        entities.map { it.toQuest() }
    }

    suspend fun refreshQuests() {
        try {
            val quests = api.getQuests()
            dao.insertAll(quests.map { it.toEntity() })
        } catch (e: Exception) {
            // Handle error
        }
    }
}
