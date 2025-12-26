package com.ministerofsalt.arcraidercompanion.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity for storing local app state (like last sync time, user preferences, etc.)
 * This is a simple key-value store for app-level data
 */
@Entity(tableName = "root_local_state")
data class RootLocalStateEntity(
    @PrimaryKey
    val key: String,
    val value: String,
    val lastUpdated: Long = System.currentTimeMillis()
)
