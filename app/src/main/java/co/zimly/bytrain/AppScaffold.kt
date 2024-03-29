package co.zimly.bytrain

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import co.zimly.bytrain.model.Screen
import co.zimly.bytrain.screens.Journeys
import co.zimly.bytrain.screens.LiveTrains
import co.zimly.bytrain.screens.Planner
import co.zimly.bytrain.screens.Profile
import co.zimly.bytrain.screens.planner.NewJourney

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun AppScaffold() {
    val navController = rememberNavController()
    val screens = listOf(Screen.Journeys, Screen.Planner, Screen.LiveTrains, Screen.Profile)

    Scaffold(bottomBar = {
        BottomNavigation {
            // current destination hierarchy (not to be confused with the entire back stack)
            val currentBackStackEntry by navController.currentBackStackEntryAsState()
            val currentHierarchy = currentBackStackEntry?.destination?.hierarchy

            // previous destination hierarchy (the screen which appears when navigating back)
            val previousBackStackEntry = navController.previousBackStackEntry
            val previousHierarchy = previousBackStackEntry?.destination?.hierarchy

            screens.forEach { screen ->
                // whether the current destination + previous destination belong to this screen
                val screenIsSelected = currentHierarchy?.any { it.route == screen.route } == true
                val previousMatches = previousHierarchy?.any { it.route == screen.route } == true

                BottomNavigationItem(
                    icon = { Icon(screen.icon, contentDescription = null) },
                    label = { Text(stringResource(screen.resourceId)) },
                    selected = screenIsSelected,
                    onClick = {
                        if (screenIsSelected && previousMatches) {
                            navController.popBackStack()
                        }

                        navController.navigate(screen.route) {
                            // Pop up to start to avoid building up back stack
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // Prevent multiple copies of same destination when reselecting
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    }
                )
            }
        }
    }) { innerPadding ->
        NavHost(
            navController,
            startDestination = Screen.Journeys.route,
            Modifier.padding(innerPadding)
        ) {
            composable(Screen.Journeys.route) { Journeys(navController) }

            navigation(
                startDestination = Screen.Planner.indexRoute,
                route = Screen.Planner.route
            ) {
                composable(Screen.Planner.indexRoute) { Planner(navController) }
                composable(Screen.Planner.NewJourney.route) { NewJourney(navController) }
            }

            composable(Screen.LiveTrains.route) { LiveTrains(navController) }
            composable(Screen.Profile.route) { Profile(navController) }
        }
    }
}
