package com.ministerofsalt.arcraidercompanion.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ministerofsalt.arcraidercompanion.data.local.dao.RootLocalStateDao
import com.ministerofsalt.arcraidercompanion.data.local.entity.RootLocalStateEntity

/**
 * Room database for the ARC Raiders Companion app
 */
@Database(
    entities = [RootLocalStateEntity::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun rootLocalStateDao(): RootLocalStateDao
    
    // Add more DAOs as needed:
    // abstract fun weaponDao(): WeaponDao
    // abstract fun characterDao(): CharacterDao
}
