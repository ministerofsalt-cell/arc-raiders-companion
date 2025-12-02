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
