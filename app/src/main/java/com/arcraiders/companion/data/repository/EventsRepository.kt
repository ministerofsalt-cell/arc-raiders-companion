package com.arcraiders.companion.data.repository

import com.arcraiders.companion.data.api.MetaForgeApi
import com.arcraiders.companion.data.model.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventsRepository @Inject constructor(
    private val api: MetaForgeApi
) {
    fun getEvents(): Flow<List<Event>> = flowOf(
        listOf(
            Event("1", "Supply Drop", "Rare loot available", System.currentTimeMillis() + 3600000),
            Event("2", "Raid Event", "Boss spawn incoming", System.currentTimeMillis() + 7200000)
        )
    )

    suspend fun refreshEvents() {
        try {
            // Fetch events from API when available
            val events = api.getEvents()
            // Cache events if needed
        } catch (e: Exception) {
            // Handle error
        }
    }
}
