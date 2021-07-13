package co.zimly.bytrain.screens.planner

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.zimly.bytrain.R
import co.zimly.bytrain.composables.FormButton
import co.zimly.bytrain.composables.FormRow
import co.zimly.bytrain.composables.FormSectionHeader

@Composable
fun NewJourney(navController: NavController) {
    var directTrainsOnly by rememberSaveable { mutableStateOf(false) }
    var showingAlert by rememberSaveable { mutableStateOf(false) }

    if (showingAlert) {
        AlertDialog(
            onDismissRequest = { showingAlert = false },
            confirmButton = {
                TextButton(onClick = { showingAlert = false }) {
                    Text(stringResource(R.string.ok))
                }
            },
            Modifier.padding(32.dp),
            title = { Text(stringResource(R.string.no_destination_station)) },
            text = { Text(stringResource(R.string.please_select_destination)) },
            contentColor = MaterialTheme.colors.onBackground,
        )
    }

    Column {
        TopAppBar(
            title = { Text(stringResource(R.string.new_journey)) },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = stringResource(R.string.back))
                }
            },
            actions = {
                IconButton(onClick = {
                    // TODO: handle main action
                    showingAlert = true
                }) {
                    Icon(Icons.Filled.Search, contentDescription = stringResource(R.string.find))
                }
            },
        )
        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Card {
                Box(contentAlignment = Alignment.CenterEnd) {
                    Column {
                        FormButton(onClick = { /*TODO*/ }) {
                            Text(stringResource(R.string.from))
                            Spacer(Modifier.width(12.dp))
                            Text(stringResource(R.string.departure_station), fontSize = 18.sp)
                            Spacer(Modifier.weight(1f))
                        }
                        Divider()
                        FormButton(onClick = { /*TODO*/ }) {
                            Text(stringResource(R.string.to))
                            Spacer(Modifier.width(12.dp))
                            Text(stringResource(R.string.destination_station), fontSize = 18.sp)
                            Spacer(Modifier.weight(1f))
                        }
                    }
                    Button(
                        onClick = { /*TODO*/ },
                        Modifier
                            .padding(end = 16.dp)
                            .size(40.dp),
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.background,
                            contentColor = MaterialTheme.colors.primary,
                        ),
                    ) {
                        Icon(
                            Icons.Filled.SwapCalls,
                            contentDescription = stringResource(R.string.swap_stations)
                        )
                    }
                }
            }

            Spacer(Modifier.height(32.dp))
            Card {
                Column {
                    FormRow {
                        // TODO: implement switch
                        Text("One-Way or Return", fontSize = 18.sp)
                    }
                    Divider()
                    FormButton(
                        onClick = { /*TODO*/ },
                        primaryText = stringResource(R.string.outbound),
                        withArrow = true,
                    ) {
                        Spacer(Modifier.weight(1f))
                        Text(
                            "15 May at 12:00",
                            fontWeight = FontWeight.Normal,
                            fontSize = 18.sp,
                        )
                    }
                }
            }

            Spacer(Modifier.height(32.dp))
            FormSectionHeader(stringResource(R.string.options))
            Card {
                Column {
                    FormButton(
                        onClick = { /*TODO*/ },
                        icon = { Icon(Icons.Filled.AltRoute, contentDescription = null) },
                        primaryText = stringResource(R.string.via_avoid),
                        withArrow = true,
                    )
                    Divider(startIndent = 16.dp + 24.dp + 16.dp)
                    FormRow(
                        Modifier.toggleable(
                            role = Role.Switch,
                            value = directTrainsOnly,
                            onValueChange = { directTrainsOnly = it },
                        ),
                        icon = { Icon(Icons.Filled.Train, contentDescription = null) },
                        primaryText = stringResource(R.string.direct_trains_only),
                    ) {
                        Spacer(Modifier.weight(1f))
                        Switch(
                            checked = directTrainsOnly,
                            onCheckedChange = null,
                        )
                    }
                }
            }

            Spacer(Modifier.height(32.dp))
            Button(
                onClick = {
                    // TODO: handle main action
                    showingAlert = true
                },
                Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Filled.Search, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text(stringResource(R.string.find_trains))
            }
        }
    }
}
