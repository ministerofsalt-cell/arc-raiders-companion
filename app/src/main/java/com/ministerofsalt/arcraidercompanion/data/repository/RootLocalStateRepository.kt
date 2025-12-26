package com.ministerofsalt.arcraidercompanion.data.repository

import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for local app state management
 */
interface RootLocalStateRepository {
    
    /**
     * Get a state value by key
     */
    fun getState(key: String): Flow<String?>
    
    /**
     * Save a state value
     */
    suspend fun saveState(key: String, value: String)
    
    /**
     * Delete a state value
     */
    suspend fun deleteState(key: String)
    
    /**
     * Clear all state
     */
    suspend fun clearAll()
}
