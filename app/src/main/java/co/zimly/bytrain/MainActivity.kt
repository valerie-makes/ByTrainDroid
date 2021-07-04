package co.zimly.bytrain

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Train
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import co.zimly.bytrain.screens.Journeys
import co.zimly.bytrain.ui.theme.ByTrainTheme

class MainActivity : ComponentActivity() {
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ByTrainTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    ByTrainApp()
                }
            }
        }
    }
}

sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    object Journeys : Screen("journeys", R.string.journeys, Icons.Filled.Map)
    object Planner : Screen("planner", R.string.planner, Icons.Filled.DateRange)
    object LiveTrains : Screen("livetrains", R.string.live_trains, Icons.Filled.Train)
    object Profile : Screen("profile", R.string.profile, Icons.Filled.Person)
}

@ExperimentalMaterialApi
@Composable
fun ByTrainApp() {
    val navController = rememberNavController()
    val screens = listOf(Screen.Journeys, Screen.Planner, Screen.LiveTrains, Screen.Profile)

    Scaffold(bottomBar = {
        BottomNavigation {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentHierarchy = navBackStackEntry?.destination?.hierarchy

            screens.forEach { screen ->
                BottomNavigationItem(
                    icon = { Icon(screen.icon, contentDescription = null) },
                    label = { Text(stringResource(screen.resourceId)) },
                    selected = currentHierarchy?.any { it.route == screen.route } == true,
                    onClick = {
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
            composable(Screen.Planner.route) { Text("Planner") }
            composable(Screen.LiveTrains.route) { Journeys(navController) }
            composable(Screen.Profile.route) { Text("Profile") }
        }
    }
}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ByTrainTheme {
        ByTrainApp()
    }
}
