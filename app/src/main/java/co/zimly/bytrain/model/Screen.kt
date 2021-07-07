package co.zimly.bytrain.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Train
import androidx.compose.ui.graphics.vector.ImageVector
import co.zimly.bytrain.R

sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    val indexRoute = "$route/index"

    object Journeys : Screen("journeys", R.string.journeys, Icons.Filled.Map)

    object Planner : Screen("planner", R.string.planner, Icons.Filled.DateRange) {
        object NewJourney {
            val route = "${Planner.route}/newjourney"
        }
    }

    object LiveTrains : Screen("livetrains", R.string.live_trains, Icons.Filled.Train)

    object Profile : Screen("profile", R.string.profile, Icons.Filled.Person)
}
