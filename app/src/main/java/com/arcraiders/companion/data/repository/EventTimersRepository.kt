package com.arcraiders.companion.data.repository

import com.arcraiders.companion.data.api.MetaForgeApi
import com.arcraiders.companion.data.model.EventTimer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventTimersRepository @Inject constructor(
    private val api: MetaForgeApi
) {
    /**
     * Get all event timers with live timing data
     * Returns Flow of event timers for reactive UI updates
     */
    fun getEventTimers(
        map: String? = null,
        name: String? = null
    ): Flow<List<EventTimer>> = flow {
        try {
            val response = api.getEventTimers(map, name)
            if (response.success) {
                emit(response.data)
            } else {
                emit(emptyList())
            }
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    /**
     * Calculate milliseconds until next event occurrence
     * @param days List of active days (e.g., ["Mon", "Wed", "Fri"])
     * @param times List of event times (e.g., ["14:00", "18:00", "22:00"])
     * @return Milliseconds until next event, or null if no upcoming event
     */
    fun calculateNextEventTime(days: List<String>, times: List<String>): Long? {
        if (days.isEmpty() || times.isEmpty()) return null
        
        val now = System.currentTimeMillis()
        val calendar = java.util.Calendar.getInstance()
        
        // Map day names to Calendar constants
        val dayMap = mapOf(
            "Sun" to java.util.Calendar.SUNDAY,
            "Mon" to java.util.Calendar.MONDAY,
            "Tue" to java.util.Calendar.TUESDAY,
            "Wed" to java.util.Calendar.WEDNESDAY,
            "Thu" to java.util.Calendar.THURSDAY,
            "Fri" to java.util.Calendar.FRIDAY,
            "Sat" to java.util.Calendar.SATURDAY
        )
        
        val dayNumbers = days.mapNotNull { dayMap[it] }
        if (dayNumbers.isEmpty()) return null
        
        var nearestEventTime: Long? = null
        
        // Check next 7 days for event occurrences
        for (dayOffset in 0..6) {
            val checkCalendar = java.util.Calendar.getInstance()
            checkCalendar.add(java.util.Calendar.DAY_OF_YEAR, dayOffset)
            val checkDay = checkCalendar.get(java.util.Calendar.DAY_OF_WEEK)
            
            if (checkDay in dayNumbers) {
                // Check all times on this day
                for (time in times) {
                    val parts = time.split(":")
                    if (parts.size != 2) continue
                    
                    val hour = parts[0].toIntOrNull() ?: continue
                    val minute = parts[1].toIntOrNull() ?: continue
                    
                    val eventCalendar = java.util.Calendar.getInstance()
                    eventCalendar.add(java.util.Calendar.DAY_OF_YEAR, dayOffset)
                    eventCalendar.set(java.util.Calendar.HOUR_OF_DAY, hour)
                    eventCalendar.set(java.util.Calendar.MINUTE, minute)
                    eventCalendar.set(java.util.Calendar.SECOND, 0)
                    eventCalendar.set(java.util.Calendar.MILLISECOND, 0)
                    
                    val eventTime = eventCalendar.timeInMillis
                    
                    if (eventTime > now) {
                        if (nearestEventTime == null || eventTime < nearestEventTime) {
                            nearestEventTime = eventTime
                        }
                    }
                }
            }
        }
        
        return if (nearestEventTime != null) {
            nearestEventTime - now
        } else {
            null
        }
    }

    /**
     * Format milliseconds into human-readable countdown
     * @param millis Milliseconds until event
     * @return Formatted string like "2h 15m" or "45m" or "5d 3h"
     */
    fun formatCountdown(millis: Long): String {
        val seconds = millis / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        
        return when {
            days > 0 -> {
                val remainingHours = hours % 24
                "${days}d ${remainingHours}h"
            }
            hours > 0 -> {
                val remainingMinutes = minutes % 60
                "${hours}h ${remainingMinutes}m"
            }
            minutes > 0 -> "${minutes}m"
            else -> "${seconds}s"
        }
    }
}
