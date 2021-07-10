package co.zimly.bytrain.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Train
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.zimly.bytrain.R
import co.zimly.bytrain.composables.SectionHeader
import co.zimly.bytrain.composables.TitleText
import co.zimly.bytrain.model.Screen
import co.zimly.bytrain.model.allStations

@ExperimentalAnimationApi
@Composable
fun Planner(navController: NavController) {
    var searchText by rememberSaveable { mutableStateOf("") }
    var searchFocused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val resultsMode = searchFocused || searchText.isNotEmpty()

    Column(
        Modifier
            .padding(top = 16.dp)
            .then(
                if (searchText.isEmpty()) Modifier.verticalScroll(rememberScrollState())
                else Modifier
            )
    ) {
        Column(Modifier.padding(horizontal = 16.dp)) {
            AnimatedVisibility(visible = !resultsMode) {
                TitleText(stringResource(R.string.planner))
            }

            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .onFocusChanged { searchFocused = it.isFocused },
                label = { Text(stringResource(R.string.search_stations)) },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                trailingIcon = {
                    Row(
                        Modifier.padding(end = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        if (searchText.isNotEmpty()) {
                            IconButton(onClick = {
                                searchText = ""
                                focusRequester.requestFocus()
                            }) {
                                Icon(
                                    Icons.Filled.Clear,
                                    contentDescription = stringResource(R.string.clear),
                                )
                            }
                        }
                        AnimatedVisibility(
                            visible = resultsMode,
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
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

            if (searchText.isEmpty()) {
                MainContent(navController)
            }
        }

        if (searchText.isNotEmpty()) {
            SearchResults(searchText)
        }
    }
}

@Composable
private fun MainContent(navController: NavController) {
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

    SectionHeader(stringResource(R.string.recents))
}

@Composable
private fun SearchResults(searchText: String) {
    val matchedStations = allStations.filter { it.name.contains(searchText, ignoreCase = true) }

    LazyColumn {
        item {
            Spacer(Modifier.height(12.dp))

            SectionHeader(
                stringResource(R.string.search_results),
                Modifier.padding(horizontal = 16.dp),
                reduceBottomPadding = true,
            )
        }

        itemsIndexed(matchedStations) { index, station ->
            TextButton(
                onClick = { /*TODO*/ },
                Modifier.fillMaxWidth(),
                shape = RectangleShape,
                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colors.onSurface),
                contentPadding = PaddingValues(
                    start = 16.dp, top = 10.dp,
                    end = 16.dp, bottom = 10.dp,
                ),
            ) {
                Icon(Icons.Filled.Train, contentDescription = null)
                Spacer(Modifier.width(14.dp))
                Text(station.name, fontSize = 18.sp)
                Spacer(Modifier.fillMaxWidth())
            }

            if (index != matchedStations.size - 1) {
                Divider(startIndent = 54.dp)
            }
        }

        item {
            Spacer(Modifier.height(12.dp))
        }
    }
}
