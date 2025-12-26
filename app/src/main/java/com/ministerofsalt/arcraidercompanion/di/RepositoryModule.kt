package com.ministerofsalt.arcraidercompanion.di

import com.ministerofsalt.arcraidercompanion.data.repository.RootLocalStateRepository
import com.ministerofsalt.arcraidercompanion.data.repository.RootLocalStateRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module that provides repository implementations
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    /**
     * Binds the RootLocalStateRepository interface to its implementation
     * This tells Hilt: "When someone asks for RootLocalStateRepository,
     * give them RootLocalStateRepositoryImpl"
     */
    @Binds
    @Singleton
    abstract fun bindRootLocalStateRepository(
        impl: RootLocalStateRepositoryImpl
    ): RootLocalStateRepository
}
