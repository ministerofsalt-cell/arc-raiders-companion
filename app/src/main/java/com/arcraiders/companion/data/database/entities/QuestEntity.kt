package com.arcraiders.companion.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quests")
data class QuestEntity(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    val objectives: String,
    val rewards: String,
    val difficulty: String,
    val isCompleted: Boolean = false,
    val progress: Int = 0,
    val lastUpdated: Long = System.currentTimeMillis()
)
