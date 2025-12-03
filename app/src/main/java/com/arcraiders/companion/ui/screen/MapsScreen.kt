package com.arcraiders.companion.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.arcraiders.companion.data.model.GameMap
import com.arcraiders.companion.data.model.POI
import com.arcraiders.companion.ui.viewmodel.MapsViewModel

@Composable
fun MapsScreen(
    viewModel: MapsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Game Maps",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        when {
            uiState.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            uiState.errorMessage != null -> {
                Text(
                    text = "Error: ${uiState.errorMessage}",
                    color = MaterialTheme.colorScheme.error
                )
            }
            else -> {
                uiState.selectedMap?.let { map ->
                    MapView(map = map, allMaps = uiState.maps, onMapSelected = viewModel::selectMap)
                }
            }
        }
    }
}

@Composable
fun MapView(
    map: GameMap,
    allMaps: List<GameMap>,
    onMapSelected: (GameMap) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Map selector dropdown
        var expanded by remember { mutableStateOf(false) }
        
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {
            OutlinedTextField(
                value = map.name,
                onValueChange = {},
                readOnly = true,
                label = { Text("Select Map") },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                allMaps.forEach { gameMap ->
                    DropdownMenuItem(
                        text = { Text(gameMap.name) },
                        onClick = {
                            onMapSelected(gameMap)
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Map placeholder (would be image in production)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Map Image: ${map.imageUrl}")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // POI list
        Text(
            text = "Points of Interest",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(map.points) { poi ->
                POICard(poi = poi)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun POICard(poi: POI) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = poi.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Type: ${poi.type}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Location: (${poi.latitude}, ${poi.longitude})",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
