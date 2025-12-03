package com.arcraiders.companion.data.repository

import com.arcraiders.companion.data.api.MetaForgeApi
import com.arcraiders.companion.data.model.Trader
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for managing trader data
 * Fetches live trader information from MetaForge API
 */
@Singleton
class TradersRepository @Inject constructor(
    private val api: MetaForgeApi
) {
    /**
     * Get all traders from the API
     * @return Flow<List<Trader>> Live stream of trader data
     */
    fun getTraders(): Flow<List<Trader>> = flow {
        try {
            val traders = api.getTraders()
            emit(traders)
        } catch (e: Exception) {
            // Handle error - emit empty list
            emit(emptyList())
        }
    }

    /**
     * Get a specific trader by name
     * @param traderName Name of the trader to find
     * @return Flow<Trader?> The trader if found, null otherwise
     */
    fun getTraderByName(traderName: String): Flow<Trader?> = flow {
        try {
            val traders = api.getTraders()
            val trader = traders.find { it.name.equals(traderName, ignoreCase = true) }
            emit(trader)
        } catch (e: Exception) {
            // Handle error
            emit(null)
        }
    }

    /**
     * Search traders by inventory item
     * @param itemName Name of the item to search for
     * @return Flow<List<Trader>> Traders that have the item in their inventory
     */
    fun searchTradersByItem(itemName: String): Flow<List<Trader>> = flow {
        try {
            val traders = api.getTraders()
            val filteredTraders = traders.filter { trader ->
                trader.inventory.any { item ->
                    item.name.contains(itemName, ignoreCase = true)
                }
            }
            emit(filteredTraders)
        } catch (e: Exception) {
            // Handle error - emit empty list
            emit(emptyList())
        }
    }
}
