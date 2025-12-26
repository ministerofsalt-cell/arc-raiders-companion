package com.ministerofsalt.arcraidercompanion.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ministerofsalt.arcraidercompanion.data.local.entity.RootLocalStateEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO for accessing local app state
 */
@Dao
interface RootLocalStateDao {
    
    @Query("SELECT * FROM root_local_state WHERE key = :key")
    fun getState(key: String): Flow<RootLocalStateEntity?>
    
    @Query("SELECT * FROM root_local_state")
    fun getAllStates(): Flow<List<RootLocalStateEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveState(state: RootLocalStateEntity)
    
    @Query("DELETE FROM root_local_state WHERE key = :key")
    suspend fun deleteState(key: String)
    
    @Query("DELETE FROM root_local_state")
    suspend fun clearAll()
}
