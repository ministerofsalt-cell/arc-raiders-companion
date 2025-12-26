package com.ministerofsalt.arcraidercompanion.data.repository

import com.ministerofsalt.arcraidercompanion.data.local.dao.RootLocalStateDao
import com.ministerofsalt.arcraidercompanion.data.local.entity.RootLocalStateEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Implementation of RootLocalStateRepository
 */
class RootLocalStateRepositoryImpl @Inject constructor(
    private val dao: RootLocalStateDao
) : RootLocalStateRepository {
    
    override fun getState(key: String): Flow<String?> {
        return dao.getState(key).map { it?.value }
    }
    
    override suspend fun saveState(key: String, value: String) {
        dao.saveState(
            RootLocalStateEntity(
                key = key,
                value = value,
                lastUpdated = System.currentTimeMillis()
            )
        )
    }
    
    override suspend fun deleteState(key: String) {
        dao.deleteState(key)
    }
    
    override suspend fun clearAll() {
        dao.clearAll()
    }
}
