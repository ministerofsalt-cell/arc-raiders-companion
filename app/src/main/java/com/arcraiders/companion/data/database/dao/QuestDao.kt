package com.arcraiders.companion.data.database.dao

import androidx.room.*
import com.arcraiders.companion.data.database.entities.QuestEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestDao {
    @Query("SELECT * FROM quests ORDER BY name ASC")
    fun getAllQuests(): Flow<List<QuestEntity>>
    
    @Query("SELECT * FROM quests WHERE id = :questId")
    suspend fun getQuestById(questId: String): QuestEntity?
    
    @Query("SELECT * FROM quests WHERE isCompleted = 0")
    fun getActiveQuests(): Flow<List<QuestEntity>>
    
    @Query("SELECT * FROM quests WHERE isCompleted = 1")
    fun getCompletedQuests(): Flow<List<QuestEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(quests: List<QuestEntity>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(quest: QuestEntity)
    
    @Update
    suspend fun update(quest: QuestEntity)
    
    @Query("UPDATE quests SET isCompleted = :completed WHERE id = :questId")
    suspend fun updateCompletion(questId: String, completed: Boolean)
    
    @Query("UPDATE quests SET progress = :progress WHERE id = :questId")
    suspend fun updateProgress(questId: String, progress: Int)
    
    @Delete
    suspend fun delete(quest: QuestEntity)
    
    @Query("DELETE FROM quests")
    suspend fun deleteAll()
}
