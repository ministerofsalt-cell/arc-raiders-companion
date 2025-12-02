package com.arcraiders.companion.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class ItemEntity(
    @PrimaryKey val id: String,
    val name: String,
    val description: String?,
    val rarity: String,
    val category: String,
    val iconUrl: String?,
    val stats: String?,
    val lastUpdated: Long = System.currentTimeMillis()
)
