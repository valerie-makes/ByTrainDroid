package co.zimly.bytrain.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import co.zimly.bytrain.R
import co.zimly.bytrain.composables.FormButton
import co.zimly.bytrain.composables.JourneyCard
import co.zimly.bytrain.composables.SectionHeader
import co.zimly.bytrain.composables.TitleText
import co.zimly.bytrain.model.JourneyViewModel
import co.zimly.bytrain.model.Screen
import co.zimly.bytrain.model.allStations
import co.zimly.bytrain.util.observeAsState
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun Planner(navController: NavController) {
    var searchText by rememberSaveable { mutableStateOf("") }
    var searchFocused by remember { mutableStateOf(false) }
    val resultsMode = searchFocused || searchText.isNotEmpty()

    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    // While scrolling down, immediately hide keyboard
    if (searchFocused && scrollState.value > 0) {
        focusManager.clearFocus()
    }

    Column(
        if (searchText.isEmpty()) {
            Modifier.verticalScroll(scrollState)
        } else {
            Modifier
        }.padding(top = 16.dp)
    ) {
        Column(Modifier.padding(horizontal = 16.dp)) {
            AnimatedVisibility(visible = searchText.isEmpty()) {
                TitleText(stringResource(R.string.planner))
            }

            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .onFocusChanged { state ->
                        scope
                            .launch {
                                if (state.isFocused) scrollState.animateScrollTo(0)
                            }
                            .invokeOnCompletion {
                                searchFocused = state.isFocused
                            }
                    },
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
            Box {
                SearchResults(searchText)
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(32.dp)
                        .background(
                            Brush.verticalGradient(
                                listOf(MaterialTheme.colors.background, Color.Transparent)
                            )
                        )
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun MainContent(
    navController: NavController,
    journeyViewModel: JourneyViewModel = viewModel(),
) {
    val favorites by journeyViewModel.favorites.observeAsState()
    val recents by journeyViewModel.recents.observeAsState()

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

    if (favorites.isNotEmpty()) {
        SectionHeader(stringResource(R.string.favorites))
        favorites.forEach {
            JourneyCard(it.from, it.to, Modifier.padding(bottom = 12.dp))
        }
    }

    if (recents.isNotEmpty()) {
        SectionHeader(stringResource(R.string.recents))
        recents.forEach {
            JourneyCard(it.from, it.to, Modifier.padding(bottom = 12.dp))
        }
    }

    if (favorites.isNotEmpty() || recents.isNotEmpty()) {
        // 12.dp + 4.dp = 16.dp
        Spacer(Modifier.height(4.dp))
    } else {
        // 8.dp + 8.dp = 16.dp
        Spacer(Modifier.height(8.dp))
    }
}

@Composable
private fun SearchResults(searchText: String) {
    val listState = rememberLazyListState()
    val focusManager = LocalFocusManager.current

    // While scrolling, immediately hide keyboard
    if (listState.isScrollInProgress) {
        focusManager.clearFocus()
    }

    val matchedStations = allStations.filter { it.name.contains(searchText, ignoreCase = true) }

    LazyColumn(state = listState) {
        item {
            Spacer(Modifier.height(12.dp))

            SectionHeader(
                stringResource(R.string.search_results),
                Modifier.padding(horizontal = 16.dp),
                reduceBottomPadding = true,
            )
        }

        itemsIndexed(matchedStations) { index, station ->
            val stationString = buildAnnotatedString {
                append(station.name)

                val matchIndex = station.name.indexOf(searchText, ignoreCase = true)
                addStyle(
                    SpanStyle(
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colors.onBackground
                    ),
                    start = matchIndex,
                    end = matchIndex + searchText.length,
                )
            }

            FormButton(
                onClick = { /*TODO*/ },
                icon = { Icon(Icons.Filled.Train, contentDescription = null) },
            ) {
                Text(stationString, fontSize = 18.sp)
                Spacer(Modifier.weight(1f))
            }

            if (index != matchedStations.size - 1) {
                Divider(startIndent = 16.dp + 24.dp + 16.dp)
            }
        }

        item {
            Spacer(Modifier.height(12.dp))
        }
    }
}
