package co.zimly.bytrain.screens.planner

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import co.zimly.bytrain.R

@Composable
fun NewJourney(navController: NavController) {
    TopAppBar(
        title = { Text(stringResource(R.string.new_journey)) },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = stringResource(R.string.back))
            }
        },
        actions = {
            IconButton(onClick = {}) {
                Icon(Icons.Filled.Search, contentDescription = stringResource(R.string.find))
            }
        },
    )
}
