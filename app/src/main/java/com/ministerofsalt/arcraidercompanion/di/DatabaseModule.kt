package com.ministerofsalt.arcraidercompanion.di

import android.content.Context
import androidx.room.Room
import com.ministerofsalt.arcraidercompanion.data.local.AppDatabase
import com.ministerofsalt.arcraidercompanion.data.local.dao.RootLocalStateDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module that provides database and DAO instances
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "arc_raiders_database"
        )
        .fallbackToDestructiveMigration() // During development only
        .build()
    }
    
    @Provides
    @Singleton
    fun provideRootLocalStateDao(database: AppDatabase): RootLocalStateDao {
        return database.rootLocalStateDao()
    }
    
    // Add more DAO providers as you create them
    // @Provides
    // @Singleton
    // fun provideWeaponDao(database: AppDatabase): WeaponDao {
    //     return database.weaponDao()
    // }
}
