package com.arcraiders.companion.ui.tools

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arcraiders.companion.data.repository.HideoutRepository

/**
 * Combined tools screen with Hideout Tracker and Item Calculator
 */
@Composable
fun ToolsScreen(
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Hideout", "Calculator")
    
    Column(modifier = modifier.fillMaxSize()) {
        TabRow(selectedTabIndex = selectedTab) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title) }
                )
            }
        }
        
        when (selectedTab) {
            0 -> HideoutTrackerTab()
            1 -> ItemCalculatorTab()
        }
    }
}

@Composable
fun HideoutTrackerTab() {
    var modules by remember { mutableStateOf(listOf<HideoutRepository.HideoutModule>()) }
    
    LaunchedEffect(Unit) {
        // Load hideout modules
        modules = listOf(
            HideoutRepository.HideoutModule(
                id = "generator",
                name = "Generator",
                description = "Powers your hideout facilities",
                level = 1,
                maxLevel = 3,
                requirements = listOf(
                    HideoutRepository.HideoutRequirement("Scrap Metal", 50),
                    HideoutRepository.HideoutRequirement("Copper Wire", 25),
                    HideoutRepository.HideoutRequirement("Fuel Can", 10)
                ),
                benefits = "Increased power capacity"
            ),
            HideoutRepository.HideoutModule(
                id = "storage",
                name = "Storage",
                description = "Increases stash capacity",
                level = 1,
                maxLevel = 5,
                requirements = listOf(
                    HideoutRepository.HideoutRequirement("Metal Sheets", 30),
                    HideoutRepository.HideoutRequirement("Bolts", 40)
                ),
                benefits = "+50 stash slots"
            ),
            HideoutRepository.HideoutModule(
                id = "workbench",
                name = "Workbench",
                description = "Craft weapons and mods",
                level = 1,
                maxLevel = 4,
                requirements = listOf(
                    HideoutRepository.HideoutRequirement("Tools", 20),
                    HideoutRepository.HideoutRequirement("Weapon Parts", 15)
                ),
                benefits = "Weapon crafting unlocked"
            )
        )
    }
    
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(modules) { module ->
            HideoutModuleCard(module = module)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HideoutModuleCard(module: HideoutRepository.HideoutModule) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = module.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Lvl ${module.level}/${module.maxLevel}",
                    style = MaterialTheme.typography.labelLarge
                )
            }
            
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = module.description,
                style = MaterialTheme.typography.bodyMedium
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Requirements:",
                style = MaterialTheme.typography.titleSmall
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            module.requirements.forEach { req ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = req.itemName,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "x${req.quantity}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Benefits: ${module.benefits}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun ItemCalculatorTab() {
    var itemName by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("1") }
    var calculatedItems by remember { mutableStateOf(listOf<CalculatedItem>()) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Crafting Calculator",
            style = MaterialTheme.typography.headlineSmall
        )
        
        OutlinedTextField(
            value = itemName,
            onValueChange = { itemName = it },
            label = { Text("Item Name") },
            modifier = Modifier.fillMaxWidth()
        )
        
        OutlinedTextField(
            value = quantity,
            onValueChange = { quantity = it },
            label = { Text("Quantity") },
            modifier = Modifier.fillMaxWidth()
        )
        
        Button(
            onClick = {
                // Calculate crafting requirements
                val qty = quantity.toIntOrNull() ?: 1
                calculatedItems = calculateCraftingRequirements(itemName, qty)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calculate Requirements")
        }
        
        if (calculatedItems.isNotEmpty()) {
            Text(
                text = "Required Items:",
                style = MaterialTheme.typography.titleMedium
            )
            
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(calculatedItems) { item ->
                    ItemRequirementRow(item = item)
                }
            }
        }
    }
}

@Composable
fun ItemRequirementRow(item: CalculatedItem) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = item.name,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "x${item.quantity}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

data class CalculatedItem(
    val name: String,
    val quantity: Int
)

fun calculateCraftingRequirements(itemName: String, quantity: Int): List<CalculatedItem> {
    // Sample crafting recipes
    return when (itemName.lowercase()) {
        "medkit" -> listOf(
            CalculatedItem("Bandages", 2 * quantity),
            CalculatedItem("Antiseptic", 1 * quantity),
            CalculatedItem("Painkillers", 1 * quantity)
        )
        "ammo box" -> listOf(
            CalculatedItem("Gunpowder", 10 * quantity),
            CalculatedItem("Metal", 5 * quantity),
            CalculatedItem("Casing", 20 * quantity)
        )
        "armor plate" -> listOf(
            CalculatedItem("Steel", 8 * quantity),
            CalculatedItem("Kevlar", 4 * quantity),
            CalculatedItem("Bolts", 6 * quantity)
        )
        else -> listOf(
            CalculatedItem("Unknown Recipe", 0)
        )
    }
}
