package com.arcraiders.companion.data.api

import com.arcraiders.companion.data.model.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MetaForgeApi {
    
    @GET("items")
    suspend fun getItems(
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 20,
        @Query("rarity") rarity: String? = null,
        @Query("category") category: String? = null
    ): ItemsResponse
    
    @GET("items/{id}")
    suspend fun getItemById(
        @Path("id") itemId: String
    ): ItemDetail
    
    @GET("quests")
    suspend fun getQuests(): List<Quest>
    
    @GET("quests/{id}")
    suspend fun getQuestById(
        @Path("id") questId: String
    ): Quest
    
    @GET("events")
    suspend fun getEvents(): List<GameEvent>
    
    @GET("events/{id}")
    suspend fun getEventById(
        @Path("id") eventId: String
    ): GameEvent
    
    @GET("maps")
    suspend fun getMaps(): List<GameMap>
    
    @GET("maps/{id}")
    suspend fun getMapById(
        @Path("id") mapId: String
    ): GameMap
    
    @GET("crafting")
    suspend fun getCraftingRecipes(): List<CraftingRecipe>
    
    @GET("crafting/{id}")
    suspend fun getCraftingRecipeById(
        @Path("id") recipeId: String
    ): CraftingRecipe
}
