package com.arcraiders.companion.di

import com.arcraiders.companion.data.api.MetaForgeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://metaforge-api.arc-raiders.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideMetaForgeApi(retrofit: Retrofit): MetaForgeApi {
        return retrofit.create(MetaForgeApi::class.java)
    }
}
