package com.arcraiders.companion.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ItemsResponse(
    val items: List<Item>,
    val pagination: Pagination
)

@Serializable
data class Item(
    val id: String,
    val name: String,
    val description: String?,
    val rarity: String,
    val category: String,
    val iconUrl: String?,
    val stats: Map<String, String>? = null
)

@Serializable
data class ItemDetail(
    val id: String,
    val name: String,
    val description: String,
    val rarity: String,
    val category: String,
    val iconUrl: String?,
    val stats: Map<String, String>? = null
)

@Serializable
data class Quest(
    val id: String,
    val name: String,
    val description: String,
    val objectives: List<String>,
    val rewards: List<String>,
    val difficulty: String
)

@Serializable
data class GameEvent(
    val id: String,
    val name: String,
    val description: String,
    val startTime: String,
    val endTime: String,
    val isActive: Boolean
)

@Serializable
data class GameMap(
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String?,
    val pointsOfInterest: List<POI>? = null
)

@Serializable
data class POI(
    val id: String,
    val name: String,
    val description: String,
    val x: Float,
    val y: Float,
    val type: String
)

@Serializable
data class CraftingComponent(
    val itemId: String,
    val quantity: Int
)

@Serializable
data class CraftingRecipe(
    val id: String,
    val name: String,
    val description: String,
    val category: String,
    val iconUrl: String?,
    val state: Map<String, String>?,
    val craftingRecipe: List<CraftingComponent>? = null,
    val locations: List<String>? = null
)

@Serializable
data class Pagination(
    val page: Int,
    val pageSize: Int,
    val totalItems: Int,
    val totalPages: Int
)

// Event Timers - Live countdown data for in-game events
@Serializable
data class EventTimersResponse(
    val success: Boolean,
    val data: List<EventTimer>
)

@Serializable
data class EventTimer(
    val game: String,                // Game name
    val name: String,                // Event name
    val map: String?,                // Map where event occurs
    val icon: String?,               // Icon URL
    val description: String?,        // Event description
    val days: List<String>,          // Days event is active (e.g., ["Mon", "Wed", "Fri"])
    val times: List<String>          // Times event occurs (e.g., ["14:00", "18:00", "22:00"])
)

// Traders - NPC trader inventories
@Serializable
data class TradersResponse(
    val success: Boolean,
    val data: Map<String, TraderInventory>  // Map of trader name to inventory
)

@Serializable
data class TraderInventory(
    val name: String,                // Trader name
    val items: List<TraderItem>      // Items sold by trader
)

@Serializable
data class TraderItem(
    val id: String,
    val name: String,
    val price: Int?,                 // Price in currency
    val stock: Int?,                 // Stock quantity
    val requiresQuest: String?       // Quest requirement if any
)

// ARCs - Missions/activities with loot data  
@Serializable
data class ArcsResponse(
    val success: Boolean,
    val data: List<ArcMission>
)

@Serializable
data class ArcMission(
    val id: String,
    val name: String,
    val description: String?,
    val difficulty: String?,         // Easy, Medium, Hard, etc.
    val rewards: List<String>?,      // List of reward item IDs
    val location: String?,           // Map/location name
    val estimatedTime: String?       // Estimated completion time
)
