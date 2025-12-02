package com.arcraiders.companion.data.repository

import com.arcraiders.companion.data.api.MetaForgeApi
import com.arcraiders.companion.data.database.dao.ItemDao
import com.arcraiders.companion.data.database.entities.ItemEntity
import com.arcraiders.companion.data.model.Item
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemsRepository @Inject constructor(
    private val api: MetaForgeApi,
    private val itemDao: ItemDao
) {
    fun getAllItems(): Flow<List<ItemEntity>> = itemDao.getAllItems()
    
    fun getItemsByCategory(category: String): Flow<List<ItemEntity>> = 
        itemDao.getItemsByCategory(category)
    
    fun searchItems(query: String): Flow<List<ItemEntity>> = 
        itemDao.searchItems(query)
    
    suspend fun refreshItems(): Result<Unit> = try {
        val response = api.getItems()
        if (response.isSuccessful && response.body() != null) {
            val items = response.body()!!.data.map { item ->
                ItemEntity(
                    id = item.id,
                    name = item.name,
                    description = item.description,
                    rarity = item.rarity,
                    category = item.category,
                    iconUrl = item.iconUrl,
                    stats = item.stats?.toString()
                )
            }
            itemDao.insertAll(items)
            Result.success(Unit)
        } else {
            Result.failure(Exception("Failed to fetch items: ${response.code()}"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
    
    suspend fun getItemById(id: String): ItemEntity? = itemDao.getItemById(id)
}
