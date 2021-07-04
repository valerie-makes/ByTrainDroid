package co.zimly.bytrain.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import co.zimly.bytrain.R
import co.zimly.bytrain.ui.composables.TitleText

@ExperimentalAnimationApi
@Composable
fun Planner(navController: NavController) {
    var searchText by rememberSaveable { mutableStateOf("") }
    var searchFocused by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val resultsMode = searchFocused || (searchText != "")

    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        AnimatedVisibility(visible = !resultsMode) {
            TitleText(stringResource(R.string.planner))
        }

        Box(contentAlignment = Alignment.CenterEnd) {
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                Modifier
                    .fillMaxWidth()
                    .onFocusChanged { searchFocused = it.isFocused },
                label = { Text("Search Stations") },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                singleLine = true,
            )
            if (resultsMode) {
                TextButton(
                    onClick = {
                        searchText = ""
                        focusManager.clearFocus()
                    },
                    Modifier.padding(top = 8.dp, end = 8.dp)
                ) {
                    Text("Cancel")
                }
            }
        }
    }
}
