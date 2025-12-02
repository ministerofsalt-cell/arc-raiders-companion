package com.arcraiders.companion.data.database.dao

import androidx.room.*
import com.arcraiders.companion.data.database.entities.ItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Query("SELECT * FROM items ORDER BY name ASC")
    fun getAllItems(): Flow<List<ItemEntity>>
    
    @Query("SELECT * FROM items WHERE id = :itemId")
    suspend fun getItemById(itemId: String): ItemEntity?
    
    @Query("SELECT * FROM items WHERE category = :category")
    fun getItemsByCategory(category: String): Flow<List<ItemEntity>>
    
    @Query("SELECT * FROM items WHERE name LIKE '%' || :query || '%'")
    fun searchItems(query: String): Flow<List<ItemEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<ItemEntity>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ItemEntity)
    
    @Update
    suspend fun update(item: ItemEntity)
    
    @Delete
    suspend fun delete(item: ItemEntity)
    
    @Query("DELETE FROM items")
    suspend fun deleteAll()
}
