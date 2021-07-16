package co.zimly.bytrain.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Map
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.zimly.bytrain.LocalSheetController
import co.zimly.bytrain.R
import co.zimly.bytrain.composables.SectionHeader
import co.zimly.bytrain.composables.TitleText
import co.zimly.bytrain.model.Screen
import co.zimly.bytrain.sheets.ByTrainPro
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun Journeys(navController: NavController) {
    val scope = rememberCoroutineScope()
    var isRefreshing by remember { mutableStateOf(false) }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = {
            isRefreshing = true
            scope.launch {
                delay(500)
                isRefreshing = false
            }
        }
    ) {
        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            MainContent(navController)
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun MainContent(navController: NavController) {
    val sheetController = LocalSheetController.current
    sheetController.BackHandler()

    TitleText(stringResource(R.string.journeys))

    SectionHeader(stringResource(R.string.upcoming))
    Card(Modifier.fillMaxWidth(), contentColor = MaterialTheme.colors.onSurface) {
        Column(
            Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(Icons.Filled.Map, contentDescription = null)
            Spacer(Modifier.height(4.dp))
            Text(
                stringResource(R.string.no_journeys),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
            )
            Text(
                stringResource(R.string.planner_cta),
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(12.dp))
            Button(
                onClick = {
                    navController.navigate(Screen.Planner.route) {
                        popUpTo(Screen.Journeys.route) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Filled.DateRange, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text(stringResource(R.string.start_planning))
            }
        }
    }

    Spacer(Modifier.height(12.dp))

    SectionHeader(stringResource(R.string.featured))
    Card(
        onClick = {
            sheetController.present { ByTrainPro() }
        },
        Modifier.fillMaxWidth(),
        contentColor = MaterialTheme.colors.onSurface,
        role = Role.Button
    ) {
        Column(
            Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val byTrainPro = stringResource(R.string.bytrain_pro)
            Row(
                Modifier.semantics { contentDescription = byTrainPro },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painterResource(R.drawable.icon),
                    contentDescription = null,
                    Modifier.size(28.dp),
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    stringResource(R.string.app_name),
                    color = MaterialTheme.colors.onBackground,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    stringResource(R.string.pro_badge),
                    Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colors.primary)
                        .padding(horizontal = 6.dp, vertical = 1.dp),
                    color = MaterialTheme.colors.onPrimary,
                    fontWeight = FontWeight.Bold,
                )
            }
            Spacer(Modifier.height(8.dp))
            Text(
                stringResource(R.string.pro_cta),
                textAlign = TextAlign.Center,
            )
        }
    }
}
