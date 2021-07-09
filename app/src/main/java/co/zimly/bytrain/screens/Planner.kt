package co.zimly.bytrain.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import co.zimly.bytrain.R
import co.zimly.bytrain.composables.Section
import co.zimly.bytrain.composables.TitleText
import co.zimly.bytrain.model.Screen

@ExperimentalAnimationApi
@Composable
fun Planner(navController: NavController) {
    var searchText by rememberSaveable { mutableStateOf("") }
    var searchFocused by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val resultsMode = searchFocused || searchText.isNotEmpty()

    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        AnimatedVisibility(visible = !resultsMode) {
            TitleText(stringResource(R.string.planner))
        }

        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            Modifier
                .fillMaxWidth()
                .onFocusChanged { searchFocused = it.isFocused },
            label = { Text(stringResource(R.string.search_stations)) },
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
            trailingIcon = {
                Row(
                    Modifier.padding(end = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (searchText.isNotEmpty()) {
                        IconButton(onClick = { searchText = "" }) {
                            Icon(
                                Icons.Filled.Clear,
                                contentDescription = stringResource(R.string.clear),
                            )
                        }
                    }
                    AnimatedVisibility(visible = resultsMode, enter = fadeIn(), exit = fadeOut()) {
                        TextButton(
                            onClick = {
                                searchText = ""
                                focusManager.clearFocus()
                            },
                        ) {
                            Text(stringResource(R.string.cancel))
                        }
                    }
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Search,
            ),
            keyboardActions = KeyboardActions(onSearch = {
                if (searchText.isNotEmpty()) {
                    focusManager.clearFocus()
                }
            }),
        )

        Spacer(Modifier.height(16.dp))
        Button(
            onClick = {
                navController.navigate(Screen.Planner.NewJourney.route)
            },
            Modifier.fillMaxWidth(),
        ) {
            Icon(Icons.Filled.Create, contentDescription = null)
            Spacer(Modifier.width(ButtonDefaults.IconSpacing))
            Text(stringResource(R.string.new_journey))
        }

        Spacer(Modifier.height(8.dp))
        Section(stringResource(R.string.recents)) {

        }
    }
}
