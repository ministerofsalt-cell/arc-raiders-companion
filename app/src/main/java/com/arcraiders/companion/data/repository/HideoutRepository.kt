package com.arcraiders.companion.data.repository

import com.arcraiders.companion.data.model.Item
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for managing hideout upgrade data
 * Provides hideout module information and tracking
 */
@Singleton
class HideoutRepository @Inject constructor() {
    
    /**
     * Hideout module data class
     */
    data class HideoutModule(
        val id: String,
        val name: String,
        val description: String,
        val level: Int,
        val maxLevel: Int,
        val requirements: List<HideoutRequirement>,
        val benefits: String,
        val isCompleted: Boolean = false
    )
    
    /**
     * Hideout requirement data class
     */
    data class HideoutRequirement(
        val itemName: String,
        val quantity: Int,
        val hasEnough: Boolean = false
    )
    
    /**
     * Get all hideout modules
     * Returns predefined hideout upgrade modules until API is available
     */
    fun getHideoutModules(): Flow<List<HideoutModule>> = flow {
        // Predefined hideout modules (can be replaced with API call when available)
        val modules = listOf(
            HideoutModule(
                id = "generator",
                name = "Generator",
                description = "Powers your hideout facilities",
                level = 1,
                maxLevel = 3,
                requirements = listOf(
                    HideoutRequirement("Scrap Metal", 50),
                    HideoutRequirement("Copper Wire", 25),
                    HideoutRequirement("Fuel Can", 10)
                ),
                benefits = "Increased power capacity, faster energy regeneration"
            ),
            HideoutModule(
                id = "storage",
                name = "Storage",
                description = "Increases stash capacity",
                level = 1,
                maxLevel = 5,
                requirements = listOf(
                    HideoutRequirement("Metal Sheets", 30),
                    HideoutRequirement("Bolts", 40),
                    HideoutRequirement("Plastic", 20)
                ),
                benefits = "+50 stash slots"
            ),
            HideoutModule(
                id = "medical",
                name = "Medical Station",
                description = "Craft medical supplies",
                level = 1,
                maxLevel = 3,
                requirements = listOf(
                    HideoutRequirement("Medical Supplies", 25),
                    HideoutRequirement("Clean Water", 15),
                    HideoutRequirement("Bandages", 30)
                ),
                benefits = "Unlock advanced healing item crafting"
            ),
            HideoutModule(
                id = "workbench",
                name = "Workbench",
                description = "Craft weapons and mods",
                level = 1,
                maxLevel = 4,
                requirements = listOf(
                    HideoutRequirement("Tools", 20),
                    HideoutRequirement("Weapon Parts", 15),
                    HideoutRequirement("Scrap Metal", 40)
                ),
                benefits = "Unlock weapon modification and crafting"
            ),
            HideoutModule(
                id = "intelligence",
                name = "Intelligence Center",
                description = "Research and planning",
                level = 1,
                maxLevel = 3,
                requirements = listOf(
                    HideoutRequirement("Electronics", 30),
                    HideoutRequirement("Data Drives", 10),
                    HideoutRequirement("Computer Parts", 15)
                ),
                benefits = "Access mission intel and map data"
            ),
            HideoutModule(
                id = "security",
                name = "Security System",
                description = "Protect your hideout",
                level = 1,
                maxLevel = 3,
                requirements = listOf(
                    HideoutRequirement("Cameras", 10),
                    HideoutRequirement("Sensors", 20),
                    HideoutRequirement("Batteries", 15)
                ),
                benefits = "Early warning system for raids"
            )
        )
        emit(modules)
    }
    
    /**
     * Update module completion status
     * In production, this would sync with backend/database
     */
    fun toggleModuleCompletion(moduleId: String, completed: Boolean): Flow<Boolean> = flow {
        // This would update local database or backend API
        emit(true)
    }
}
