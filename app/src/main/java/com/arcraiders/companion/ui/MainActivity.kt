package com.arcraiders.companion.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.arcraiders.companion.ui.screen.*
import com.arcraiders.companion.ui.theme.ARCRaidersCompanionTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ARCRaidersCompanionTheme {
                ArcRaidersApp()
            }
        }
    }
}

// Navigation destinations
sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Items : Screen("items", "Items", Icons.Default.ShoppingBag)
    object Events : Screen("events", "Events", Icons.Default.CalendarToday)
    object Quests : Screen("quests", "Quests", Icons.Default.List)
    object Maps : Screen("maps", "Maps", Icons.Default.Map)
    object Skills : Screen("skills", "Skills", Icons.Default.AccountTree)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArcRaidersApp() {
    val navController = rememberNavController()
    val items = listOf(
        Screen.Items,
        Screen.Events,
        Screen.Quests,
        Screen.Maps,
        Screen.Skills
    )
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ARC Raiders Companion") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Items.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Items.route) { ItemsScreen() }
            composable(Screen.Events.route) { EventsScreen() }
            composable(Screen.Quests.route) { QuestsScreen() }
            composable(Screen.Maps.route) { MapsScreen() }
            composable(Screen.Skills.route) { SkillTreeScreen() }
        }
    }
}
